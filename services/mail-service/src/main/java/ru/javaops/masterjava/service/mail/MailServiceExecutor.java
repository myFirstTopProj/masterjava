package ru.javaops.masterjava.service.mail;

import akka.dispatch.Futures;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import ru.javaops.masterjava.service.mail.util.MailUtils;
import ru.javaops.masterjava.service.mail.util.MailUtils.MailObject;
import ru.javaops.web.WebStateException;
import scala.concurrent.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
public class MailServiceExecutor {

    private static final String INTERRUPTED_BY_FAULTS_NUMBER = "+++ Interrupted by faults number";
    private static final String INTERRUPTED_BY_TIMEOUT = "+++ Interrupted by timeout";
    private static final String INTERRUPTED_EXCEPTION = "+++ InterruptedException";

    private static final ExecutorService mailExecutor = Executors.newFixedThreadPool(8);

    public static GroupResult sendBulk(final Set<Addressee> addressees, final String subject, final String body, List<Attach> attaches) {
        final CompletionService<MailResult> completionService = new ExecutorCompletionService<>(mailExecutor);

        List<Future<MailResult>> futures = StreamEx.of(addressees)
                .map(addressee -> completionService.submit(() -> MailSender.sendTo(addressee, subject, body, attaches)))
                .toList();

        return new Callable<GroupResult>() {
            private int success = 0;
            private List<MailResult> failed = new ArrayList<>();

            @Override
            public GroupResult call() {
                while (!futures.isEmpty()) {
                    try {
                        Future<MailResult> future = completionService.poll(10, TimeUnit.SECONDS);
                        if (future == null) {
                            return cancelWithFail(INTERRUPTED_BY_TIMEOUT);
                        }
                        futures.remove(future);
                        MailResult mailResult = future.get();
                        if (mailResult.isOk()) {
                            success++;
                        } else {
                            failed.add(mailResult);
                            if (failed.size() >= 5) {
                                return cancelWithFail(INTERRUPTED_BY_FAULTS_NUMBER);
                            }
                        }
                    } catch (ExecutionException e) {
                        return cancelWithFail(e.getCause().toString());
                    } catch (InterruptedException e) {
                        return cancelWithFail(INTERRUPTED_EXCEPTION);
                    }
                }
                GroupResult groupResult = new GroupResult(success, failed, null);
                log.info("groupResult: {}", groupResult);
                return groupResult;
            }

            private GroupResult cancelWithFail(String cause) {
                futures.forEach(f -> f.cancel(true));
                return new GroupResult(success, failed, cause);
            }
        }.call();
    }

    public static scala.concurrent.Future<GroupResult> sendAsyncWithReply(MailObject mailObject, ExecutionContext ec) {
        // http://doc.akka.io/docs/akka/current/java/futures.html
        return Futures.future(
                () -> sendBulk(MailUtils.split(mailObject.getUsers()), mailObject.getSubject(), mailObject.getBody(), MailUtils.getAttaches(mailObject.getAttaches())),
                ec);
    }

    public static void sendAsync(MailObject mailObject) {
        Set<Addressee> addressees = MailUtils.split(mailObject.getUsers());
        addressees.forEach(addressee ->
                mailExecutor.submit(() -> {
                    try {
                        MailSender.sendTo(addressee, mailObject.getSubject(), mailObject.getBody(), MailUtils.getAttaches(mailObject.getAttaches()));
                    } catch (WebStateException e) {
                        // already logged
                    }
                })
        );
    }
}
package ru.javaops.masterjava.service.mail;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import ru.javaops.web.AuthUtil;
import ru.javaops.web.WebStateException;
import ru.javaops.web.WsClient;
import ru.javaops.web.handler.SoapClientLoggingHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.soap.MTOMFeature;
import java.util.List;
import java.util.Set;

@Slf4j
public class MailWSClient {
    private static final WsClient<MailService> WS_CLIENT;
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    private static final SoapClientLoggingHandler LOGGING_HANDLER = new SoapClientLoggingHandler(Level.DEBUG);

    public static String AUTH_HEADER = AuthUtil.encodeBasicAuthHeader(USER, PASSWORD);

    static {
        WS_CLIENT = new WsClient<MailService>(Resources.getResource("wsdl/mailService.wsdl"),
                new QName("http://mail.javaops.ru/", "MailServiceImplService"),
                MailService.class);

        WS_CLIENT.init("mail", "/mail/mailService?wsdl");
    }


    public static String sendToGroup(final Set<Addressee> to, final Set<Addressee> cc, final String subject, final String body, List<Attach> attaches) throws WebStateException {
        log.info("Send mail to '" + to + "' cc '" + cc + "' subject '" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        String status;
        try {
            status = getPort().sendToGroup(to, cc, subject, body, attaches);
            log.info("Sent with status: " + status);
        } catch (Exception e) {
            log.error("sendToGroup failed", e);
            throw WsClient.getWebStateException(e);
        }
        return status;
    }

    public static GroupResult sendBulk(final Set<Addressee> to, final String subject, final String body, List<Attach> attaches) throws WebStateException {
        log.info("Send mail to '" + to + "' subject '" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        GroupResult result;
        try {
            result = getPort().sendBulk(to, subject, body, attaches);
        } catch (WebStateException e) {
            log.error("sendBulk failed", e);
            throw WsClient.getWebStateException(e);
        }
        log.info("Sent with result: " + result);
        return result;
    }

    private static MailService getPort() {
        MailService port = WS_CLIENT.getPort(new MTOMFeature(1024));
        WsClient.setAuth(port, USER, PASSWORD);
        WsClient.setHandler(port, LOGGING_HANDLER);
        return port;
    }

    public static Set<Addressee> split(String addressees) {
        Iterable<String> split = Splitter.on(',').trimResults().omitEmptyStrings().split(addressees);
        return ImmutableSet.copyOf(Iterables.transform(split, Addressee::new));
    }
}

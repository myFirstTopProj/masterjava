package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ru.javaops.web.WebStateException;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MailServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:8080/mail/mailService?wsdl"),
                new QName("http://mail.javaops.ru/", "MailServiceImplService"));

        MailService mailService = service.getPort(MailService.class);

        ImmutableSet<Addressee> addressees = ImmutableSet.of(
                new Addressee("Мастер Java <masterjava@javaops.ru>"));

        List<Attach> attaches = ImmutableList.of(
                new Attach("version.html", new DataHandler(new File("config_templates/version.html").toURI().toURL())));

        try {
            String status = mailService.sendToGroup(addressees, ImmutableSet.of(), "Bulk email subject", "Bulk email body", attaches);
            System.out.println(status);

            GroupResult groupResult = mailService.sendBulk(addressees, "Individual mail subject", "Individual mail body", attaches);
            System.out.println(groupResult);
        } catch (WebStateException e) {
            System.out.println(e);
        }
    }
}

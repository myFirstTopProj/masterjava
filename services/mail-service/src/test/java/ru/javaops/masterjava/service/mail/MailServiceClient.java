package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableList;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class MailServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:8080/mail/mailService?wsdl"),
                new QName("http://mail.javaops.ru/", "MailServiceImplService"));

        MailService mailService = service.getPort(MailService.class);

        mailService.sendMail(ImmutableList.of(
                new Addressee("gkislin@javaops.ru"),
                new Addressee("Bad Email <bad_email.ru>")), ImmutableList.of(), "Subject", "Body");

        mailService.sendMail(
                ImmutableList.of(new Addressee("Григорий Кислин <gkislin@javaops.ru>")),
                ImmutableList.of(new Addressee("Мастер Java <masterjava@javaops.ru>")), "Subject", "Body");
    }
}

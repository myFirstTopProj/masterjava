package ru.javaops.masterjava.service.mail;

import javax.jws.WebService;
import java.util.Set;

@WebService(endpointInterface = "ru.javaops.masterjava.service.mail.MailService", targetNamespace = "http://mail.javaops.ru/"
//          , wsdlLocation = "WEB-INF/wsdl/mailService.wsdl"
)
public class MailServiceImpl implements MailService {
    public void sendMail(Set<Addressee> to, Set<Addressee> cc, String subject, String body) {
        MailSender.sendBulk(to, cc, subject, body);
    }
}
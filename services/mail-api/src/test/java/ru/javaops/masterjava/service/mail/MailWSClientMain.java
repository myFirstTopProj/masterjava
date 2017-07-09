package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import java.io.File;

@Slf4j
public class MailWSClientMain {
    public static void main(String[] args) {
        ImmutableSet<Addressee> addressees = ImmutableSet.of(
                new Addressee("Мастер Java <masterjava@javaops.ru>"));

        try {
            String state = MailWSClient.sendToGroup(addressees, ImmutableSet.of(), "Subject", "Body", ImmutableList.of(
                    new Attach("version.html", new DataHandler(new File("config_templates/version.html").toURI().toURL()))
            ));
            System.out.println(state);
        } catch (Throwable e) {
            log.error(e.toString(), e);
        }
    }
}
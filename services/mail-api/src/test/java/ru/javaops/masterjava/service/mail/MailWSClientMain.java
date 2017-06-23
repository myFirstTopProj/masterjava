package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableSet;

public class MailWSClientMain {
    public static void main(String[] args) {
        MailWSClient.sendToGroup(
                ImmutableSet.of(new Addressee("Григорий Кислин <gkislin@javaops.ru>")),
                ImmutableSet.of(new Addressee("Мастер Java <masterjava@javaops.ru>")), "Subject", "Body");
    }
}
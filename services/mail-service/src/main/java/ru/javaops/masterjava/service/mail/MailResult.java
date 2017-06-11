package ru.javaops.masterjava.service.mail;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailResult {
    public static final String OK = "OK";

    private final String email;
    private final String result;

    public boolean isOk() {
        return OK.equals(result);
    }

    @Override
    public String toString() {
        return '(' + email + ',' + result + ')';
    }
}

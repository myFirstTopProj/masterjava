package ru.javaops.masterjava.service.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String OK = "OK";

    private @NonNull
    String email;
    private String result;

    public boolean isOk() {
        return OK.equals(result);
    }

    @Override
    public String toString() {
        return '\'' + email + "' result '" + result + '\'';
    }
}

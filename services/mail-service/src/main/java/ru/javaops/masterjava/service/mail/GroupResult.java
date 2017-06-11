package ru.javaops.masterjava.service.mail;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GroupResult {
    private final int success; // number of successfully sent email
    private final List<MailResult> failed; // failed emails with causes
    private final String failedCause;  // global fail cause

    @Override
    public String toString() {
        return "Success: " + success + '\n' +
                "Failed: " + failed.toString() + '\n' +
                (failedCause == null ? "" : "Failed cause" + failedCause);
    }
}

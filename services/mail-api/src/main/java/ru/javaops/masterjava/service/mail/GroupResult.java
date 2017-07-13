package ru.javaops.masterjava.service.mail;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private int success; // number of successfully sent email
    private List<MailResult> failed; // failed emails with causes
    private String failedCause;  // global fail cause

    public GroupResult(Exception e) {
        this(-1, null, Throwables.getRootCause(e).toString());
    }

    @Override
    public String toString() {
        return "Success: " + success + '\n' +
                (failed == null ? "" : "Failed: " + failed.toString() + '\n') +
                (failedCause == null ? "" : "Failed cause: " + failedCause);
    }
}
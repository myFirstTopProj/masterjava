package ru.javaops.masterjava.webapp;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import ru.javaops.masterjava.service.mail.util.MailUtils;
import ru.javaops.masterjava.util.Functions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
public class WebUtil {

    public static void doAndWriteResponse(HttpServletResponse resp, Functions.SupplierEx<String> doer) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String result;
        try {
            log.info("Start processing");
            result = doer.get();
            log.info("Processing finished with result: {}", result);
        } catch (Exception e) {
            log.error("Processing failed", e);
            String message = e.getMessage();
            result = (message != null) ? message : e.getClass().getName();
        }
        resp.getWriter().write(result);
    }

    public static String getNotEmptyUsers(HttpServletRequest req) {
        String users = req.getParameter("users");
        checkArgument(!Strings.isNullOrEmpty(users), "Addresses are not selected");
        return users;
    }

    public static String getNotEmptyParam(HttpServletRequest req, String param) {
        String value = req.getParameter(param);
        checkArgument(!Strings.isNullOrEmpty(value), param + " must not be empty");
        return value;
    }

    public static MailUtils.MailObject createMailObject(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("attach");

        List<AbstractMap.SimpleImmutableEntry<String, byte[]>> attaches;
        if (filePart.getSize() == 0) {
            attaches = ImmutableList.of();
        } else {
            attaches = ImmutableList.of(
                    new AbstractMap.SimpleImmutableEntry<>(filePart.getSubmittedFileName(), IOUtils.toByteArray(filePart.getInputStream()))
            );
        }
        return new MailUtils.MailObject(getNotEmptyUsers(req), req.getParameter("subject"), getNotEmptyParam(req, "body"), attaches);
    }
}

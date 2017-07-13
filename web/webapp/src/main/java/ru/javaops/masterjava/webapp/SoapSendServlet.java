package ru.javaops.masterjava.webapp;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.service.mail.Attach;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.service.mail.util.MailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

import static ru.javaops.masterjava.webapp.WebUtil.*;

@WebServlet("/sendSoap")
@Slf4j
@MultipartConfig
public class SoapSendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doAndWriteResponse(resp, () -> {
            String users = getNotEmptyUsers(req);
            String subject = req.getParameter("subject");
            String body = getNotEmptyParam(req, "body");
            List<Attach> attaches;
            Part filePart = req.getPart("attach");
            if (filePart == null) {
                attaches = ImmutableList.of();
            } else {
                attaches = ImmutableList.of(MailUtils.getAttach(filePart.getSubmittedFileName(), filePart.getInputStream()));
            }
            return MailWSClient.sendBulk(MailUtils.split(users), subject, body, attaches).toString();
        });
    }
}

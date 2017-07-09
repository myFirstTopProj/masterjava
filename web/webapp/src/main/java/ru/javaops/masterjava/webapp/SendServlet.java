package ru.javaops.masterjava.webapp;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.service.mail.Attach;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.service.mail.util.Attachments;
import ru.javaops.web.WebStateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/send")
@Slf4j
@MultipartConfig
public class SendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String users = req.getParameter("users");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");
        List<Attach> attaches;
        Part filePart = req.getPart("attach");
        if (filePart == null) {
            attaches = ImmutableList.of();
        } else {
            attaches = ImmutableList.of(Attachments.getAttach(filePart.getName(), filePart.getInputStream()));
        }
        String groupResult;
        try {
            groupResult = MailWSClient.sendBulk(MailWSClient.split(users), subject, body, attaches).toString();
        } catch (WebStateException e) {
            groupResult = e.toString();
        }
        resp.getWriter().write(groupResult);
    }
}

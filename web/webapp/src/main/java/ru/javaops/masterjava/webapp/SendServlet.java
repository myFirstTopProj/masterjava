package ru.javaops.masterjava.webapp;

import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailWSClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/send")
@Slf4j
public class SendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String users = req.getParameter("users");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");
        GroupResult groupResult = MailWSClient.sendBulk(MailWSClient.split(users), subject, body);
        resp.getWriter().write(groupResult.toString());
    }
}

package ru.javaops.masterjava.webapp;

import lombok.extern.slf4j.Slf4j;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.IllegalStateException;

@WebServlet("/sendJms")
@Slf4j
public class JmsSendServlet extends HttpServlet {
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            InitialContext initCtx = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initCtx.lookup("java:comp/env/jms/ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer((Destination) initCtx.lookup("java:comp/env/jms/queue/MailQueue"));
        } catch (Exception e) {
            throw new IllegalStateException("JMS init failed", e);
        }
    }

    @Override
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ex) {
                log.warn("Couldn't close JMSConnection: ", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String users = req.getParameter("users");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");
        resp.getWriter().write(sendJms(users, subject, body));
    }

    private synchronized String sendJms(String users, String subject, String body) {
        String msg;
        try {
            TextMessage testMessage = session.createTextMessage();
            testMessage.setText(subject);
            producer.send(testMessage);
            msg = "Successfully sent message.";
            log.info(msg);
        } catch (Exception e) {
            msg = "Sending JMS message failed: " + e.getMessage();
            log.error(msg, e);
        }
        return msg;
    }
}
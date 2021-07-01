package com.codecool.shop.service;

import com.codecool.shop.controller.ConfirmationController;
import com.codecool.shop.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    public void sendMail(HttpServletResponse resp, Customer customer, String mes) throws IOException, MessagingException {

        String to = customer.getEmail();

        String host = "smtp.gmail.com";

        final String from = "deeznutsshop666@gmail.com";
        final String password = "nuclearkernel";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your latest transaction");
            message.setText(mes);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.info(String.format("Messaging exception: %s", e.getMessage()));
            e.printStackTrace();
        }
    }
}

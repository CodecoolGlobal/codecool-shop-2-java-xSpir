package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.CustomerDaoMem;
import com.codecool.shop.model.Customer;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = {"/confirmation"}, name = "confirmation")
public class ConfirmationController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDao customerDao = CustomerDaoMem.getInstance();
        StringBuilder jsonString = new StringBuilder();
        try {
            String line = "";
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jsonString.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> dict = new HashMap<>();
        String[] keysValues =
                jsonString.toString().replace("\"", "")
                        .replace("{", "")
                        .replace("}", "")
                        .split(",");
        for (String kvPair : keysValues)
            dict.put(kvPair.split(":")[0], kvPair.split(":")[1]);

        String paymentSource = dict.containsKey("card") ? dict.get("card") : dict.get("username");

        saveJSONFile(dict);
        Customer customer = getCustomer(dict);
        customerDao.add(customer);
        try {
            sendMail(resp, customer);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // roleplay
        pay("");
        resp.sendRedirect(String.format("http://localhost:8080/%s",
                checkPayment(paymentSource) ? "?redir=payment_fail" : ("success?" + paymentSource)));
        resp.setStatus(200);
    }

    private void saveJSONFile(HashMap<String, String> dict) {
        JSONObject transaction = new JSONObject();
        try {
            transaction.put("First name", dict.get("first_name"));
            transaction.put("Last name", dict.get("last_name"));
            transaction.put("Post code", dict.get("post_code"));
            transaction.put("City", dict.get("city"));
            transaction.put("Address", dict.get("address"));
            transaction.put("Email address", dict.get("email"));
            transaction.put("Card number", dict.get("card"));
            transaction.put("Username", dict.get("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray transaxion = new JSONArray();
        transaxion.add(transaction);
        String filename = "transaction";
        try (FileWriter file = new FileWriter(filename + ".json")) {
            file.append(transaxion.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pay(String auth) {
    }

    private boolean checkPayment(String source) {
        int x = 0;
        for (int i = 0; i < source.length(); i++) {
            x += source.charAt(i);
        }
        return x % 2 == 0;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDataStore = CartDaoMem.getLegacyInstance();
        CartDaoMem.destroyCurrentInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if (cartDataStore.getAll() != null) {
            context.setVariable("products", cartDataStore.getAll());
            context.setVariable("total", ((CartDaoMem) cartDataStore).getTotalPrice());
        }

        engine.process("product/postorder.html", context, resp.getWriter());
    }

    private Customer getCustomer(HashMap<String, String> dict) throws IOException {
        return new Customer(dict.get("first_name"), dict.get("last_name"), dict.get("post_code"),
                dict.get("city"), dict.get("address"), dict.get("email"));
    }

    private void sendMail(HttpServletResponse resp, Customer customer) throws IOException, MessagingException {

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
        String mes = getMessage(customer);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your latest transaction");
            message.setText(mes);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getMessage(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(",\n")
                .append("Thank you for your order! It is being processed at the moment. It will be shipped to: ").append(customer.getPostCode()).append(", ")
                .append(customer.getCity()).append(" ").append(customer.getAddress()).append(" between 12-20 workdays.\n")
                .append("We are delighted you chose the NutShop, take care in these trying times!\n\n").append("Sincerely,\n").append("The Nut Crew");
        return sb.toString();
    }
}

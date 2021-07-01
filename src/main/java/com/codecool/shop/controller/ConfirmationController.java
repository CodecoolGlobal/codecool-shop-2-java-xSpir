package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.CustomerDaoMem;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.EmailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.slf4j.Logger;

import javax.mail.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/confirmation"}, name = "confirmation")
public class ConfirmationController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmationController.class);
    private EmailService mailer = new EmailService();

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
            logger.warn(String.format("Couldn't read JSON data. Check sending side. Error message: %s", e.getMessage()));
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
            mailer.sendCustomerMail(resp, customer, orderConfirmationMessage(customer));
        } catch (MessagingException e) {
            logger.info(String.format("Unsuccessful email sending attempt. Trace: %s", e.getMessage()));
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
            logger.warn("");
            e.printStackTrace();
        }
        JSONArray transaxion = new JSONArray();
        transaxion.add(transaction);
        String filename = "transaction";
        try (FileWriter file = new FileWriter(filename + ".json")) {
            file.append(transaxion.toJSONString());
            file.flush();
        } catch (IOException e) {
            logger.info(String.format("File writing failed, because: %s", e.getMessage()));
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

    private String orderConfirmationMessage(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(",\n")
                .append("Thank you for your order! It is being processed at the moment. It will be shipped to: ").append(customer.getPostCode()).append(", ")
                .append(customer.getCity()).append(" ").append(customer.getAddress()).append(" between 12-20 workdays.\n")
                .append("We are delighted you chose the NutShop, take care in these trying times!\n\n").append("Sincerely,\n").append("The Nut Crew");
        return sb.toString();
    }
}

package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.json.JSONException;
import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = "";
        try {
            String line = "";
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jsonString += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("jsonString = " + jsonString);
        // example: {"first_name":"Balint","last_name":"Molnar","card":"326263462345235457547467"}
        HashMap<String, String> dict = new HashMap<>();
        String[] keysValues =
                jsonString.replace("\"", "")
                        .replace("{", "")
                        .replace("}", "")
                        .split(",");
        for (String kvPair : keysValues)
            dict.put(kvPair.split(":")[0], kvPair.split(":")[1]);

        String paymentSource = dict.containsKey("card") ? dict.get("card") : dict.get("username");

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

        // roleplay
        pay("");
        resp.sendRedirect(String.format("http://localhost:8080/%s",
                checkPayment(paymentSource) ? "?redir=payment_fail" : ("success?" + paymentSource)));
        resp.setStatus(200);
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
}

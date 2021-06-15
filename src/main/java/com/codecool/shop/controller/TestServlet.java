package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "testServlet", urlPatterns = "/testServlet")
public class TestServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        System.out.println(response);

        Employee employee = new Employee(1, "Karan", "IT", 5000);

        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        List<Product> products = productDaoMem.getAll();

        /*
        int size = products.size();
        int packetSize = 2;
        for (int i = 0; i < Math.ceil(size / (double)packetSize); i++) {
            String message = this.gson.toJson(products.subList(i * packetSize, (i + 1) * packetSize));
            System.out.println(message);
        }
        */

        String messageString = this.gson.toJson(products);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageString);
        out.flush();
    }
}
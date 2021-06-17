package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "testServlet", urlPatterns = "/testServlet")
public class TestServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        System.out.println(response);

        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        List<Product> products = productDaoMem.getAll();

        JsonArray jsonArray = new JsonArray();


//        for (Product product : products){
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("namek",product.getName());
//            jsonObject.addProperty("description",product.getDescription());
//            jsonObject.addProperty("id",String.valueOf(product.getId()));
//            jsonObject.addProperty("defaultPrice",String.valueOf(product.getDefaultPrice()));
//            jsonObject.addProperty("supplier",product.getSupplier().getName());
//            jsonArray.add(jsonObject);
//        }

        /*
        int size = products.size();
        int packetSize = 2;
        for (int i = 0; i < Math.ceil(size / (double)packetSize); i++) {
            String message = this.gson.toJson(products.subList(i * packetSize, (i + 1) * packetSize));
            System.out.println(message);
        }
        */

        String messageString = this.gson.toJson(products);
        System.out.println(jsonArray);
        String testString = this.gson.toJson(jsonArray);


        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageString);
        out.flush();
    }
}
//package com.codecool.shop.controller;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.LinkedList;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.codecool.shop.model.Product;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@WebServlet("/jsonservlet")
//public class JsonServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    // This will store all received articles
//    List<Product> products = new LinkedList<Product>();
//
//    /***************************************************
//     * URL: /jsonservlet
//     * doPost(): receives JSON data, parse it, map it and send back as JSON
//     ****************************************************/
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException{
//
//        // 1. get received JSON data from request
//        BufferedReader br =
//                new BufferedReader(new InputStreamReader(request.getInputStream()));
//
//        String json = "";
//        if(br != null){
//            json = br.readLine();
//            System.out.println(json);
//        }
//
//        // 2. initiate jackson mapper
//        ObjectMapper mapper = new ObjectMapper();
//
//        // 3. Convert received JSON to Article
//        Product product = mapper.readValue(json, Product.class);
//
//        // 4. Set response type to JSON
//        response.setContentType("application/json");
//
//        // 5. Add article to List<Article>
//        products.add(product);
//
//        // 6. Send List<Article> as JSON to client
//        mapper.writeValue(response.getOutputStream(), products);
//    }
//}

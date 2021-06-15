package com.codecool.shop.controller;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "testServlet", urlPatterns = "/testServlet")
class TestServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        System.out.println(response);

        Employee employee = new Employee(1, "Karan", "IT", 5000);
        String employeeJsonString = this.gson.toJson(employee);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }
}
package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.CartProduct;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cartapi"})
public class CartApiController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDataStore = CartDaoMem.getInstance();
        PrintWriter out = resp.getWriter();

        if (req.getParameterMap().containsKey("id")) {
            if (cartDataStore.find(Integer.parseInt(req.getParameter("id"))) != null) {
                int quantity = cartDataStore.find(Integer.parseInt(req.getParameter("id"))).getQuantity();
                String price = cartDataStore.find(Integer.parseInt(req.getParameter("id"))).getDefaultPrice() * quantity +
                        " " + cartDataStore.find(Integer.parseInt(req.getParameter("id"))).getDefaultCurrency();
                String data = quantity + " " + price;
                out.println(data);
            }
            else out.println("null");
        }
        else out.println("null");
    }
}

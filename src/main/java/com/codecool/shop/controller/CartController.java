package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"}, name = "cart")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDataStore = CartDaoMem.getInstance();

        if (req.getParameterMap().containsKey("add")) {
            ProductDao productDataStore = ProductDaoMem.getInstance();
            Product prod = productDataStore.find(Integer.parseInt(req.getParameter("add")));
            if (prod != null) cartDataStore.add(prod);

            resp.sendRedirect("/cart");
        }
        else if (req.getParameterMap().containsKey("rmf")) {
            if (cartDataStore.find(Integer.parseInt(req.getParameter("rmf"))) != null) cartDataStore.removeFully(Integer.parseInt(req.getParameter("rmf")));

            resp.sendRedirect("/cart");
        }

        else if (req.getParameterMap().containsKey("rm")) {
            if (cartDataStore.find(Integer.parseInt(req.getParameter("rm"))) != null) cartDataStore.remove(Integer.parseInt(req.getParameter("rm")));

            resp.sendRedirect("/cart");
        }

        else {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("products", cartDataStore.getAll());
            context.setVariable("useJs", true);

            engine.process("product/cart.html", context, resp.getWriter());
        }
    }
}

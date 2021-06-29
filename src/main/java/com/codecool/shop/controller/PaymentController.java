package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"}, name = "payment")
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PaymentDao paymentDataStore = PaymentDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();

        if (cartDataStore.getAll().size() == 0) {
            resp.sendRedirect("http://localhost:8080/?redir=empty_cart");
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariable("category", productService.getProductCategory(1));
        if (cartDataStore.getAll() != null) {
            context.setVariable("products", cartDataStore.getAll());
            context.setVariable("total", ((CartDaoMem) cartDataStore).getTotalPrice());
        }


        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/payment.html", context, resp.getWriter());
    }
}

package com.codecool.shop.controller;

import com.codecool.shop.controller.JDBC.DatabaseManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet(urlPatterns = {"/"}, name = "index")
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
        try {
            DataSource dataSource = new DatabaseManager().connect();
            ProductDaoJDBC productDataStore = new ProductDaoJDBC(dataSource);
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();
            ProductService productService = new ProductService(productDataStore,productCategoryDataStore);
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());

            context.setVariable("categories", productService.getEveryProductCategory());
            context.setVariable("suppliers", supplierDaoMem.getAll());
            context.setVariable("products", productService.getEveryProducts());
            engine.process("product/index.html", context, resp.getWriter());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }





        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);

    }

}

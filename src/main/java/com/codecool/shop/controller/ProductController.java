package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.JDBC.DatabaseManager;
import com.codecool.shop.controller.logger.OurLogger;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/"}, name = "index")
public class ProductController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductDao productDataStore;
        try {
            DataSource dataSource = new DatabaseManager().connect();
            if (DatabaseManager.getDao().equals("memory")) productDataStore = ProductDaoMem.getInstance();
            else productDataStore = new ProductDaoJDBC(dataSource);
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();
            ProductService productService = new ProductService(productDataStore, productCategoryDataStore);
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());

            context.setVariable("categories", productService.getEveryProductCategory());
            context.setVariable("suppliers", supplierDaoMem.getAll());
            context.setVariable("products", productService.getEveryProducts());
            engine.process("product/index.html", context, resp.getWriter());
        } catch (SQLException throwables) {
            OurLogger.log("Error while rendering page: " + throwables.getMessage());
            logger.info("Error while rendering page: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

}

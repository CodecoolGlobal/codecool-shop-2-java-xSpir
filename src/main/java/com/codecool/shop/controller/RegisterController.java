package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.JDBC.DatabaseManager;
import com.codecool.shop.controller.logger.OurLogger;
import com.codecool.shop.service.EmailService;
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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns = {"/register"}, name = "register")
public class RegisterController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("register.html", context, resp.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        try {
            DataSource dataSource = new DatabaseManager().connect();
            Connection conn = dataSource.getConnection();
            String sql = "INSERT INTO customer(name, email, password) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("You are sucessfully registered");
                EmailService mailer = new EmailService();
                mailer.sendRegistrationMail(response, email, registrationConfirmationMessage(name));
                TimeUnit.SECONDS.sleep(3);
                response.sendRedirect("/");
            }


        } catch (Exception se) {
            OurLogger.log("Registration failed: " + se.getMessage());
            logger.info("Registration failed: " + se.getMessage());
            se.printStackTrace();
        }
    }

    private String registrationConfirmationMessage(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(name).append(",\n")
                .append("We are delighted to welcome you as the newest member of the Nut Shop Community!\n\n").append("Sincerely,\n").append("The Nut Crew");
        return sb.toString();
    }
}

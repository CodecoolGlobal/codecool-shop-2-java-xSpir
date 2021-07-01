package com.codecool.shop.controller.JDBC;

import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static Object dao;

    public DataSource connect() throws SQLException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "connection.properties";
        String catalogConfigPath = rootPath + "catalog";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            dao = appProps.get("dao");
        } catch (IOException e) {
            logger.info("IO Exception while searching for properties file: " + e.getMessage());
            e.printStackTrace();
        }
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(appProps.get("database").toString());
        dataSource.setUser(appProps.get("user").toString());
        dataSource.setPassword(appProps.get("password").toString());

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    public static Object getDao() {
        return dao;
    }
}

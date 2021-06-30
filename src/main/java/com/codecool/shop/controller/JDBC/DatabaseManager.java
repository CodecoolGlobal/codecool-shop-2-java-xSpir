package com.codecool.shop.controller.JDBC;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    public DataSource connect() throws SQLException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "connection.properties";
        String catalogConfigPath = rootPath + "catalog";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            System.out.println("appProps = " + appProps);
        } catch (IOException e) { //TODO: logger
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
}

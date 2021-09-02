package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.logger.OurLogger;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJDBC.class);

    public ProductDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product (default_price, name, default_currency, description, category, supplier) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setFloat(1, product.getDefaultPrice());
            st.setString(2, product.getName());
            st.setString(3, product.getDefaultCurrency().toString());
            st.setString(4, product.getDescription());
            st.setString(5, product.getProductCategory().toString());
            st.setString(6, product.getSupplier().toString());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
        } catch (SQLException throwables) {
            logger.info("Error while adding new Product: " + throwables.getMessage());
            throw new RuntimeException("Error while adding new Product: ", throwables);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            return new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3),
                    rs.getString(4), rs.getString(5), rs.getObject(6, ProductCategory.class), rs.getObject(7, Supplier.class));
        } catch (SQLException e) {
            OurLogger.log("Error while finding Product: " + e.getMessage());
            logger.info("Error while finding Product: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            ProductCategoryDaoJDBC productCategoryDaoJDBC = new ProductCategoryDaoJDBC();
            SupplierDaoJDBC supplierDaoJDBC = new SupplierDaoJDBC();
            String sql = "SELECT * FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                ProductCategory category = productCategoryDaoJDBC.find(rs.getInt(6));
                Supplier supplier = supplierDaoJDBC.find(rs.getInt(7));
                Product product = new Product(rs.getInt(1), rs.getString(3), rs.getFloat(2),
                        rs.getString(4), rs.getString(5), category, supplier);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            OurLogger.log("Error while finding all Products: " + e.getMessage());
            logger.info("Error while finding all Products: " + e.getMessage());
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier spl) {
        try (Connection conn = dataSource.getConnection()) {
            ProductCategoryDaoJDBC productCategoryDaoJDBC = new ProductCategoryDaoJDBC();
            String sql = "SELECT * FROM product WHERE supplier = ?";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                ProductCategory category = productCategoryDaoJDBC.find(rs.getInt(6));
                Product product = new Product(rs.getInt(1), rs.getString(3), rs.getFloat(2),
                        rs.getString(4), rs.getString(5), category, spl);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            OurLogger.log("Error while finding Products: " + e.getMessage());
            logger.info("Error while finding Products: " + e.getMessage());
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()) {
            SupplierDaoJDBC supplierDaoJDBC = new SupplierDaoJDBC();
            String sql = "SELECT * FROM product WHERE category = ?";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                Supplier supplier = supplierDaoJDBC.find(rs.getInt(7));
                Product product = new Product(rs.getInt(1), rs.getString(3), rs.getFloat(2),
                        rs.getString(4), rs.getString(5), productCategory, supplier);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            OurLogger.log("Error while finding Products: " + e.getMessage());
            logger.info("Error while finding Products: " + e.getMessage());
            throw new RuntimeException("Error while reading all products", e);
        }
    }
}
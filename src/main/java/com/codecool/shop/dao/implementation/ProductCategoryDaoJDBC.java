package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.JDBC.DatabaseManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private DataSource dataSource = new DatabaseManager().connect();
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryDaoJDBC.class);

    public ProductCategoryDaoJDBC() throws SQLException {
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            return new ProductCategory(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
        } catch (SQLException e) {
            logger.info("Error while finding ProductCategories: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM category";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<ProductCategory> result = new ArrayList<>();
            while (rs.next()) {
                ProductCategory category = new ProductCategory(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                result.add(category);
            }
            return result;
        } catch (SQLException e) {
            logger.info("Error while finding ProductCategories: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(SupplierDaoJDBC.class);

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            return new Supplier(rs.getInt(1), rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            logger.info("Error while finding Suppliers: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM category";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Supplier> result = new ArrayList<>();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                Supplier supplier = find(rs.getInt(7));
                result.add(supplier);
            }
            return result;
        } catch (SQLException e) {
            logger.info("Error while finding Suppliers: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

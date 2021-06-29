package com.codecool.shop.dao;

import com.codecool.shop.model.CartProduct;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface CartDao {
    void add(Product product);
    CartProduct find(int id);
    void remove(int id);
    void removeFully(int id);

    List<CartProduct> getAll();
    //List<Product> getBy(Supplier supplier);
    //List<Product> getBy(ProductCategory productCategory);
}

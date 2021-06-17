package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.CartProduct;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<CartProduct> data = new ArrayList<>();
    private static CartDaoMem instance = null;
    private static CartDaoMem legacyInstance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    public static CartDaoMem getLegacyInstance() {
        if (legacyInstance == null) {
            legacyInstance = getInstance();
        }
        return legacyInstance;
    }

    public static void destroyCurrentInstance() {
        getLegacyInstance();
        instance = null;
    }

    public String getTotalPrice() {
        return data.size() > 0 ? "Total price: " + data.stream()
                .map(cartProduct -> cartProduct.getDefaultPrice() * cartProduct.getQuantity()).reduce((float) 0, Float::sum).toString() +
                " " + data.get(0).getDefaultCurrency() : null;
    }

    @Override
    public void add(Product product) {
        if (find(product.getId()) != null) find(product.getId()).add();
        else data.add(new CartProduct(product));
    }

    @Override
    public CartProduct find(int id) {
        if (data.size() == 0) return null;
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        //There is more than one in cart
        if (find(id).getQuantity() > 1) {
            find(id).remove();
        }
        //Only one in cart
        else data.remove(find(id));
    }

    @Override
    public void removeFully(int id) {
        data.remove(find(id));
    }

    @Override
    public List<CartProduct> getAll() {
        return data;
    }

    /* @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }*/
}

package com.codecool.shop.model;

public class CartProduct extends Product{
    private int quantity = 0;

    public CartProduct(Product product) {
        super(product.getId(), product.getName(), product.getDefaultPrice(), product.getDefaultCurrency().toString(), product.getDescription(), product.getProductCategory(), product.getSupplier());
        quantity = 1;
    }

    @Override
    public String getPrice() {
        return String.valueOf(this.getDefaultPrice()*quantity) + " " + this.getDefaultCurrency().toString();
    }

    public void add() {
        quantity++;
    }

    public void remove() {
        quantity--;
    }

    public int getQuantity() {
        return quantity;
    }
}

package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;

import java.util.List;

public interface CustomerDao {
    void add(Customer customer);
    Customer find(String email);
    void remove(String email);

    List<Customer> getAll();
    Customer findLast();
}

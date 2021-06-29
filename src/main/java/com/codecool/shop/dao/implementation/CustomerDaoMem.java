package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMem implements CustomerDao {
    private List<Customer> customers = new ArrayList<>();
    private static CustomerDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CustomerDaoMem() {
    }

    public static CustomerDaoMem getInstance() {
        if (instance == null) {
            instance = new CustomerDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Customer customer) {
        customers.add(0, customer);
    }

    @Override
    public Customer find(String email) {
        return customers.stream().filter(customer -> customer.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public void remove(String email) {
        customers.remove(find(email));
    }

    @Override
    public List<Customer> getAll() {
        return customers;
    }

    @Override
    public Customer findLast() {
        return customers.get(0);
    }


}

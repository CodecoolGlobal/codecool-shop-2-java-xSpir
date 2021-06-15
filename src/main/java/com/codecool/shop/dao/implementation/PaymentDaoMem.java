package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.PaymentDao;

public class PaymentDaoMem implements PaymentDao {
    private static PaymentDaoMem instance = null;

    private PaymentDaoMem() {
    }

    public static PaymentDaoMem getInstance() {
        if (instance == null) {
            instance = new PaymentDaoMem();
        }
        return instance;
    }

}

package com.codecool.shop.model;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String postCode;
    private final String city;
    private final String address;
    private final String email;
    private String cardNumber;
    private String username;

    public Customer(String firstName, String lastName, String postCode, String city, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.city = city;
        this.address = address;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

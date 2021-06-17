package com.codecool.shop.model;

public class Customer {
    private String firstName;
    private String lastName;
    private String postCode;
    private String city;
    private String address;
    private String email;
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

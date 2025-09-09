package com.koi.ecommerceapp.models;

public class User {
    public final String name;
    public final String email;
    public final String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

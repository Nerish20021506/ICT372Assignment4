
package com.koi.ecommerceapp.models;

public class Product {
    public final int id;
    public final String name;
    public final String description;
    public final double price;

    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}

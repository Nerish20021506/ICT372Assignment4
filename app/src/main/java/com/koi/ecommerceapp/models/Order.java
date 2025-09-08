
package com.koi.ecommerceapp.models;

import java.util.List;
import java.util.Date;

public class Order {
    public final String id;
    public final List<CartItem> items;
    public final double total;
    public final Date createdAt;

    public Order(String id, List<CartItem> items, double total, Date createdAt) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.createdAt = createdAt;
    }
}

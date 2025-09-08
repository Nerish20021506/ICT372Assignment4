
package com.koi.ecommerceapp.data;

import com.koi.ecommerceapp.models.CartItem;
import com.koi.ecommerceapp.models.Order;
import com.koi.ecommerceapp.models.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FakeRepository {
    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static final List<CartItem> CART = new ArrayList<>();
    private static final List<Order> ORDERS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product(1, "Kiteboard", "High performance board", 799.0));
        PRODUCTS.add(new Product(2, "Wetsuit", "All-season wetsuit", 299.0));
        PRODUCTS.add(new Product(3, "Harness", "Comfort-fit harness", 199.0));
        PRODUCTS.add(new Product(4, "Pump", "Durable hand pump", 49.0));
    }

    public static List<Product> getProducts() { return new ArrayList<>(PRODUCTS); }

    public static Product getProductById(int id) {
        for (Product p : PRODUCTS) if (p.id == id) return p;
        return null;
    }

    public static List<Product> search(String query) {
        String q = query.toLowerCase();
        return PRODUCTS.stream()
                .filter(p -> p.name.toLowerCase().contains(q) || p.description.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public static List<CartItem> getCart() { return CART; }

    public static void addToCart(Product p, int qty) {
        for (CartItem ci : CART) {
            if (ci.product.id == p.id) {
                ci.quantity += qty;
                return;
            }
        }
        CART.add(new CartItem(p, qty));
    }

    public static double cartTotal() {
        double t = 0;
        for (CartItem ci : CART) t += ci.getSubtotal();
        return t;
    }

    public static void clearCart() { CART.clear(); }

    public static List<Order> getOrders() { return ORDERS; }

    public static void placeOrder() {
        double total = cartTotal();
        if (total <= 0) return;
        Order o = new Order(UUID.randomUUID().toString(), new ArrayList<>(CART), total, new Date());
        ORDERS.add(o);
        clearCart();
    }
}

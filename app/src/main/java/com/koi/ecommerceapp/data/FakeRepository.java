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
        PRODUCTS.add(new Product(1, "iPhone 15", "Latest Apple smartphone", 1499.0));
        PRODUCTS.add(new Product(2, "Samsung Galaxy S24", "Flagship Android phone", 1399.0));
        PRODUCTS.add(new Product(3, "MacBook Pro", "16-inch M3 Pro, 16GB RAM, 1TB SSD", 3499.0));
        PRODUCTS.add(new Product(4, "Sony WH-1000XM5", "Noise-cancelling headphones", 599.0));
        PRODUCTS.add(new Product(5, "Logitech MX Master 3S", "Wireless performance mouse", 149.0));
    }

    public static List<Product> getProducts() { return new ArrayList<>(PRODUCTS); }

    public static Product getProductById(int id) {
        for (Product p : PRODUCTS) if (p.id == id) return p;
        return null;
    }

    public static List<Product> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(PRODUCTS); // return all when nothing typed
        }
        String q = query.toLowerCase();
        return PRODUCTS.stream()
                .filter(p -> p.name.toLowerCase().contains(q)
                        || p.description.toLowerCase().contains(q))
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

    // ✅ new method
    public static void removeFromCart(Product p) {
        for (int i = 0; i < CART.size(); i++) {
            if (CART.get(i).product.id == p.id) {
                CART.remove(i);
                return;
            }
        }
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

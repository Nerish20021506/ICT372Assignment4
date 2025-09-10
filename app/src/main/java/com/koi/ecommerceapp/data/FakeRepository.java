package com.koi.ecommerceapp.data;

import com.koi.ecommerceapp.models.CartItem;
import com.koi.ecommerceapp.models.Order;
import com.koi.ecommerceapp.models.Product;
import com.koi.ecommerceapp.models.User;
import com.koi.ecommerceapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FakeRepository {
    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static final List<CartItem> CART = new ArrayList<>();
    private static final List<Order> ORDERS = new ArrayList<>();
    private static final List<User> USERS = new ArrayList<>();

    // Seed products
    static {
        PRODUCTS.add(new Product(1, "HP Laptop 15-fd0739TU 15.6 inch", "Made for collaboration with a HP True Vision Camera with dual mics and noise reduction.\n\nKey Features:\n• Screen - 15.6 inch FHD (1920 x 1080) SVA Display\n• Speed - Intel Core i5 - 1334U 10 core processor (12MB L3 cache, 1.3 - 4.6GHz)\n• •\tStorage & RAM - 512GB SSD storage with 16GB RAM\n", 1119.0, R.drawable.hplaptop));
        PRODUCTS.add(new Product(2, "AirPods Pro 2", "Featuring pro-level Active Noise Cancellation1, Adaptive Audio, Transparency mode2, Personalised Spatial Audio3 and a breakthrough in hearing health with a scientifically validated Hearing Test4, clinical-grade Hearing Aid capability4 and Loud Sound Reduction.\n\nKey Features:\n• Adaptive Audio dynamically blends Transparency mode and Active Noise Cancellation to deliver the best listening experience for you in any environment\n• •\tPersonalised Spatial Audio surrounds you in sound tuned just for you\n• Charge with a USB-C connector or an Apple Watch, MagSafe or Qi-certified charger\n", 348.0, R.drawable.airpods));
        PRODUCTS.add(new Product(3, "WH-CH520", "The WH-CH520 is a lightweight on-ear wireless headphone for casual use. Long battery-life, quick charging and handsfree voice calls, with multipoint, also  make this great-value headphone an easy choice. A first for this category of headphone, the companion app provides many features, including adjustable sound and other custom settings.\n\nKey Features:\n• Sound quality: DSEE music-upscaling for better sounding music\n• Battery: Up to 50 hours battery life with quick charging\n• Enhanced feature: Multipoint connectivity and clear voice calls\n", 79.0, R.drawable.wh));
        PRODUCTS.add(new Product(4, "Phone 16 Pro Max", "The iPhone 16 Pro Max features a larger 6.9-inch display, the powerful A18 Pro chip, an upgraded camera system with a 48MP ultrawide sensor, and improved recording capabilities with 4K 120fps video in Dolby Vision. It also introduces new features like Apple Intelligence for AI tasks, Wi-Fi 7 support for faster connectivity, and a dedicated camera control button. The design includes a titanium frame, slimmer bezels, and enhanced IP68 water resistance.\n\nKey Features:\n• Built for Apple Intelligence\n• Stunning titanium design\n• Camera Control with 4K 120 fps Dolby Vision\n", 1837.0, R.drawable.iphone16));
        PRODUCTS.add(new Product(5, "Canon EOS 1500D DSLR Camera with 18-55mm Lens", "The Canon EOS 1500D is an entry-level DSLR camera with an 18-55mm lens kit designed for beginners, featuring a 24.1MP APS-C CMOS sensor and DIGIC 4+ processor for high-quality images and Full HD video. Key features include an optical viewfinder, Scene Intelligent Auto mode, built-in Wi-Fi/NFC for sharing, a 9-point autofocus system, and creative filters, making it a user-friendly option for capturing everyday moments and developing photography skills.\n\nKey Features:\n• Sensor: 24.1MP APS-C sized sensor\n• Screen: High resolution 3 inch LCD screen\n• Modes: Understand each mode as you use it with built-in guide\n", 849.0, R.drawable.canon));
    }

    // ------------------ PRODUCTS ------------------
    public static List<Product> getProducts() {
        return new ArrayList<>(PRODUCTS);
    }

    public static Product getProductById(int id) {
        for (Product p : PRODUCTS) if (p.id == id) return p;
        return null;
    }

    public static List<Product> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(PRODUCTS); // return all when nothing typed
        }
        String q = query.toLowerCase();
        // ✅ Only search in name now
        return PRODUCTS.stream()
                .filter(p -> p.name.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    // ------------------ CART ------------------
    public static List<CartItem> getCart() {
        return CART;
    }

    public static void addToCart(Product p, int qty) {
        for (CartItem ci : CART) {
            if (ci.product.id == p.id) {
                ci.quantity += qty;
                return;
            }
        }
        CART.add(new CartItem(p, qty));
    }

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

    public static void clearCart() {
        CART.clear();
    }

    // ------------------ ORDERS ------------------
    public static List<Order> getOrders() {
        return ORDERS;
    }

    public static void placeOrder() {
        double total = cartTotal();
        if (total <= 0) return;
        Order o = new Order(UUID.randomUUID().toString(), new ArrayList<>(CART), total, new Date());
        ORDERS.add(o);
        clearCart();
    }

    // ------------------ USERS ------------------
    public static boolean registerUser(String name, String email, String password) {
        for (User u : USERS) {
            if (u.email.equalsIgnoreCase(email)) {
                return false; // duplicate email
            }
        }
        USERS.add(new User(name, email, password));
        return true;
    }

    public static boolean validateUser(String email, String password) {
        for (User u : USERS) {
            if (u.email.equalsIgnoreCase(email) && u.password.equals(password)) {
                return true;
            }
        }
        return false;
    }
}

package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.adapters.CartAdapter;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.CartItem;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private TextView tvTotal;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        tvTotal = findViewById(R.id.tvTotal);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        cartItems = FakeRepository.getCart();

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(new CartAdapter(cartItems, this::updateTotal));

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                tvTotal.setText("Cart is empty");
            } else {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        tvTotal.setText(String.format(Locale.US, "Total: $%.2f", total));
    }
}

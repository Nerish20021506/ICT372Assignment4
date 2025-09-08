package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotal = findViewById(R.id.tvTotal);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
        });
    }

    private void updateTotal() {
        double total = 0.0;

        // If getCart() returns a List<CartItem>
        List<CartItem> cart = FakeRepository.getCart();
        for (CartItem item : cart) {
            total += item.product.price * item.quantity;
        }

        tvTotal.setText("Total: $" + total);
    }
}

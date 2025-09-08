package com.koi.ecommerceapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvDescription = findViewById(R.id.tvDescription);
        EditText etQty = findViewById(R.id.etQty);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnPlus = findViewById(R.id.btnPlus);

        // Get product info from intent
        int id = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("productName");
        String desc = getIntent().getStringExtra("productDescription");
        double price = getIntent().getDoubleExtra("productPrice", 0.0);

        tvName.setText(name);
        tvPrice.setText("$" + price);
        tvDescription.setText(desc);

        Product p = new Product(id, name, desc, price);

        // Quantity buttons
        btnMinus.setOnClickListener(v -> {
            int qty = getQty(etQty);
            if (qty > 1) etQty.setText(String.valueOf(qty - 1));
        });

        btnPlus.setOnClickListener(v -> {
            int qty = getQty(etQty);
            etQty.setText(String.valueOf(qty + 1));
        });

        // Add to Cart
        btnAddToCart.setOnClickListener(v -> {
            int qty = getQty(etQty);
            FakeRepository.addToCart(p, qty);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    private int getQty(EditText et) {
        try {
            return Integer.parseInt(et.getText().toString().trim());
        } catch (Exception e) {
            return 1;
        }
    }
}

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

    private Product product;

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

        // ✅ Get product by ID
        int productId = getIntent().getIntExtra("product_id", -1);
        product = FakeRepository.getProductById(productId);

        if (product != null) {
            tvName.setText(product.name);
            tvPrice.setText(String.format("$%.2f", product.price));
            tvDescription.setText(product.description);
        }

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
            if (product != null) {
                FakeRepository.addToCart(product, qty);
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
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

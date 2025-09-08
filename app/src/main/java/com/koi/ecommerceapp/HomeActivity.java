package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.adapters.ProductAdapter;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // 👈 match your actual layout name

        EditText etSearch = findViewById(R.id.etSearch);
        Button btnCart = findViewById(R.id.btnCart);
        RecyclerView rvProducts = findViewById(R.id.rvProducts);

        // --- Cart button navigation ---
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // --- Product list setup ---
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products = FakeRepository.getProducts();

        // ✅ Pass BOTH arguments required: (products, listener)
        rvProducts.setAdapter(new ProductAdapter(products, product -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("product_id", product.id); // 👈 only send id
            startActivity(intent);
        }));
    }
}

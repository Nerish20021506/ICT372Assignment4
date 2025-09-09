package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText etSearch = findViewById(R.id.etSearch);
        Button btnCart = findViewById(R.id.btnCart);
        Button btnHistory = findViewById(R.id.btnHistory);
        RecyclerView rvProducts = findViewById(R.id.rvProducts);

        // Cart button → open cart
        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        // History button → open purchase history
        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, PurchaseHistoryActivity.class));
        });

        // Load all products initially
        List<Product> products = FakeRepository.getProducts();
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, products, product -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("product_id", product.id);
            startActivity(intent);
        });
        rvProducts.setAdapter(adapter);

        // 🔎 Filter as user types
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> filtered = FakeRepository.search(s.toString());
                adapter.updateData(filtered);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}

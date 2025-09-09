package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.adapters.ProductAdapter;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra("query");
        android.widget.Toast.makeText(this, "Query: " + query, android.widget.Toast.LENGTH_SHORT).show();
        RecyclerView rvResults = findViewById(R.id.rvResults);

        List<Product> results = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            for (Product p : FakeRepository.getProducts()) {
                if (p.name.toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        }

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(new ProductAdapter(this, results, product -> {
            Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
            intent.putExtra("product_id", product.id);
            startActivity(intent);
        }));
    }
}

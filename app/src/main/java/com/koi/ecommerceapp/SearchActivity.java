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

        RecyclerView rvResults = findViewById(R.id.rvResults);
        rvResults.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra("query");
        List<Product> results = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            for (Product p : FakeRepository.getProducts()) {
                if (p.name.toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        }

        ProductAdapter adapter = new ProductAdapter(results, product -> {
            Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
            intent.putExtra("productId", product.id);
            intent.putExtra("productName", product.name);
            intent.putExtra("productDescription", product.description);
            intent.putExtra("productPrice", product.price);
            startActivity(intent);
        });
        rvResults.setAdapter(adapter);
    }
}

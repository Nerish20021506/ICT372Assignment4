package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.adapters.ProductAdapter;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private EditText etSearch;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvProducts = findViewById(R.id.rvProducts);
        etSearch = findViewById(R.id.etSearch);

        // Load products from repository
        final List<Product> allProducts = FakeRepository.getProducts();

        // Setup adapter → open detail when item clicked
        adapter = new ProductAdapter(allProducts, product -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("productId", product.id);
            intent.putExtra("productName", product.name);
            intent.putExtra("productDescription", product.description);
            intent.putExtra("productPrice", product.price);
            startActivity(intent);
        });

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(adapter);

        // Simple search → launch SearchActivity when user hits Enter
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String q = etSearch.getText() == null ? "" : etSearch.getText().toString().trim();
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            intent.putExtra("query", q);
            startActivity(intent);
            return true;
        });
    }

    // Inflate menu with Cart option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // Handle cart tap
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

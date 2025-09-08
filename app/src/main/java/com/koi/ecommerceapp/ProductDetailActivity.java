package com.koi.ecommerceapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;   // <-- important import

import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;
import com.koi.ecommerceapp.utils.IntentKeys;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Toolbar setup (activity_detail.xml must include <include layout="@layout/view_toolbar" />)
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_title));
        }

        // Get product id
        int id = getIntent().getIntExtra(IntentKeys.PRODUCT_ID, -1);
        Product p = FakeRepository.getProductById(id);
        if (p == null) {
            finish();
            return;
        }

        // Views
        ImageView imgHero = findViewById(R.id.imgHero); // optional if you added it
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvDescription = findViewById(R.id.tvDescription);
        EditText etQty = findViewById(R.id.etQty);
        Button btnAdd = findViewById(R.id.btnAddToCart);

        // Bind data
        if (imgHero != null) imgHero.setImageResource(android.R.drawable.ic_menu_gallery);
        tvName.setText(p.name);
        tvPrice.setText(getString(R.string.price_fmt, p.price));
        tvDescription.setText(p.description);

        // Qty +/- (if you added buttons in the layout; optional)
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnPlus  = findViewById(R.id.btnPlus);
        if (btnMinus != null) {
            btnMinus.setOnClickListener(v -> {
                int q = parseQty(etQty);
                if (q > 1) etQty.setText(String.valueOf(q - 1));
            });
        }
        if (btnPlus != null) {
            btnPlus.setOnClickListener(v -> etQty.setText(String.valueOf(parseQty(etQty) + 1)));
        }

        // Add to cart
        btnAdd.setOnClickListener(v -> {
            int q = Math.max(1, parseQty(etQty));
            FakeRepository.addToCart(p, q);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    private int parseQty(EditText et) {
        try { return Integer.parseInt(et.getText().toString().trim()); }
        catch (Exception e) { return 1; }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

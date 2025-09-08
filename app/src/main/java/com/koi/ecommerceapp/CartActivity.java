
package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rv = findViewById(R.id.rvCart);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<CartItem> items = FakeRepository.getCart();
        rv.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override public RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
                android.view.View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }
            @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CartItem ci = items.get(position);
                TextView t1 = holder.itemView.findViewById(android.R.id.text1);
                TextView t2 = holder.itemView.findViewById(android.R.id.text2);
                t1.setText(ci.product.name + " x" + ci.quantity);
                t2.setText(String.format("$%.2f", ci.getSubtotal()));
            }
            @Override public int getItemCount() { return items.size(); }
        });

        TextView tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText(String.format("Total: $%.2f", FakeRepository.cartTotal()));

        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));
    }
}

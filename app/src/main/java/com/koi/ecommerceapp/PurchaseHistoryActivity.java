
package com.koi.ecommerceapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PurchaseHistoryActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        RecyclerView rv = findViewById(R.id.rvOrders);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orders = FakeRepository.getOrders();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        rv.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override public RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
                android.view.View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
                return new RecyclerView.ViewHolder(v) {};
            }
            @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                Order o = orders.get(position);
                TextView t1 = holder.itemView.findViewById(android.R.id.text1);
                TextView t2 = holder.itemView.findViewById(android.R.id.text2);
                t1.setText("Order " + o.id.substring(0, 8) + " - $" + String.format("%.2f", o.total));
                t2.setText("Placed: " + sdf.format(o.createdAt));
            }
            @Override public int getItemCount() { return orders.size(); }
        });
    }
}

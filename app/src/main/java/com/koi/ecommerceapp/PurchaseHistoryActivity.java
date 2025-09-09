package com.koi.ecommerceapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        RecyclerView rvOrders = findViewById(R.id.rvOrders);
        TextView tvEmpty = findViewById(R.id.tvEmpty);

        List<Order> orders = FakeRepository.getOrders();

        if (orders.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvOrders.setVisibility(View.VISIBLE);
            rvOrders.setLayoutManager(new LinearLayoutManager(this));
            rvOrders.setAdapter(new OrdersAdapter(orders));
        }
    }

    // ✅ Inner adapter class for displaying orders
    static class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.VH> {
        private final List<Order> orders;
        private final SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.US);

        OrdersAdapter(List<Order> orders) {
            this.orders = orders;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            Order o = orders.get(position);
            holder.title.setText("Order ID: " + o.id);
            holder.subtitle.setText(
                    String.format(Locale.US, "Total: $%.2f • %s", o.total, df.format(o.createdAt))
            );
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            TextView title, subtitle;
            VH(View v) {
                super(v);
                title = v.findViewById(android.R.id.text1);
                subtitle = v.findViewById(android.R.id.text2);
            }
        }
    }
}

package com.koi.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.R;
import com.koi.ecommerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface OnProductClick {
        void onView(Product product);
    }

    private List<Product> data; // no longer final, so we can replace
    private final OnProductClick listener;

    public ProductAdapter(List<Product> data, OnProductClick listener) {
        this.data = new ArrayList<>(data); // copy so we can safely modify
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Product p = data.get(i);
        h.tvName.setText(p.name);
        h.tvPrice.setText(String.format("$%.2f", p.price));
        h.btnView.setOnClickListener(v -> listener.onView(p));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // ✅ Add this method so HomeActivity can update the list when filtering
    public void updateData(List<Product> newProducts) {
        this.data = new ArrayList<>(newProducts);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        Button btnView;
        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            btnView = v.findViewById(R.id.btnView);
        }
    }
}

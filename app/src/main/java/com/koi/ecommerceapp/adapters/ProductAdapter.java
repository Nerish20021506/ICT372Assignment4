package com.koi.ecommerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.R;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface OnProductClick {
        void onViewDetails(Product product);
    }

    private final Context context;
    private final List<Product> products;
    private final OnProductClick listener;

    public ProductAdapter(Context context, List<Product> products, OnProductClick listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Product p = products.get(position);

        holder.tvName.setText(p.name);
        holder.tvPrice.setText(String.format("$%.2f", p.price));
        holder.tvQty.setText("1");

        // – button
        holder.btnMinus.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.tvQty.getText().toString());
            if (qty > 1) holder.tvQty.setText(String.valueOf(qty - 1));
        });

        // + button
        holder.btnPlus.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.tvQty.getText().toString());
            holder.tvQty.setText(String.valueOf(qty + 1));
        });

        // Add to Cart
        holder.btnAddToCart.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.tvQty.getText().toString());
            FakeRepository.addToCart(p, qty);
        });

        // View Details (delegates to listener)
        holder.btnView.setOnClickListener(v -> {
            if (listener != null) listener.onViewDetails(p);
        });
    }
    public void updateData(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty;
        Button btnMinus, btnPlus, btnAddToCart, btnView;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvQty = v.findViewById(R.id.tvQty);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus = v.findViewById(R.id.btnPlus);
            btnAddToCart = v.findViewById(R.id.btnAddToCart);
            btnView = v.findViewById(R.id.btnView);
        }
    }
}

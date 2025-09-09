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

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface OnProductClick {
        void onView(Product product);
    }

    private final Context context;
    private List<Product> products;
    private final OnProductClick listener;

    public ProductAdapter(Context context, List<Product> products, OnProductClick listener) {
        this.context = context;
        this.products = new ArrayList<>(products);
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Product p = products.get(i);
        h.tvName.setText(p.name);
        h.tvPrice.setText(String.format("$%.2f", p.price));

        // Default qty = 1
        h.tvQty.setText("1");

        // Quantity controls
        h.btnMinus.setOnClickListener(v -> {
            int qty = Integer.parseInt(h.tvQty.getText().toString());
            if (qty > 1) {
                h.tvQty.setText(String.valueOf(qty - 1));
            }
        });

        h.btnPlus.setOnClickListener(v -> {
            int qty = Integer.parseInt(h.tvQty.getText().toString());
            h.tvQty.setText(String.valueOf(qty + 1));
        });

        h.btnAddToCart.setOnClickListener(v -> {
            int qty = Integer.parseInt(h.tvQty.getText().toString());
            FakeRepository.addToCart(p, qty);
        });

        // Whole card opens product detail
        h.itemView.setOnClickListener(v -> listener.onView(p));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateData(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty;
        Button btnMinus, btnPlus, btnAddToCart;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvQty = v.findViewById(R.id.tvQty);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus = v.findViewById(R.id.btnPlus);
            btnAddToCart = v.findViewById(R.id.btnAddToCart);
        }
    }
}

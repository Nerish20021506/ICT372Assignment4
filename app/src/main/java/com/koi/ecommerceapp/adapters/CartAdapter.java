package com.koi.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.R;
import com.koi.ecommerceapp.data.FakeRepository;
import com.koi.ecommerceapp.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    public interface OnCartChangeListener {
        void onCartUpdated();

    }

    private final List<CartItem> cartItems;
    private final OnCartChangeListener listener;

    public CartAdapter(List<CartItem> cartItems, OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvName.setText(item.product.name);
        holder.tvPrice.setText(String.format("$%.2f each, subtotal: $%.2f",
                item.product.price, item.getSubtotal()));
        holder.tvQty.setText(String.valueOf(item.quantity));

        holder.btnMinus.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                notifyItemChanged(position);
                if (listener != null) listener.onCartUpdated();
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.quantity++;
            notifyItemChanged(position);
            if (listener != null) listener.onCartUpdated();
        });

        holder.btnRemove.setOnClickListener(v -> {
            FakeRepository.removeFromCart(item.product);
            notifyItemRemoved(position);
            if (listener != null) listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty;
        Button btnMinus, btnPlus, btnRemove;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvQty = v.findViewById(R.id.tvQty);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus = v.findViewById(R.id.btnPlus);
            btnRemove = v.findViewById(R.id.btnRemove);
        }
    }
}

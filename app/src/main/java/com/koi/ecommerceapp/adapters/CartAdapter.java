package com.koi.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.ecommerceapp.R;
import com.koi.ecommerceapp.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    private final List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reuse item_product.xml so no new file needed
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvName.setText(item.product.name);

        // Show price x quantity = subtotal
        holder.tvPrice.setText(
                String.format("$%.2f x %d = $%.2f",
                        item.product.price,
                        item.quantity,
                        item.getSubtotal())
        );

        // Hide the "View Details" button in cart context
        holder.btnView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
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

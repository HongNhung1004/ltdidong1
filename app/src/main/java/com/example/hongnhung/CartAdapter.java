
package com.example.hongnhung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    private Context context;
    private List<CartItem> cartItems;
    private OnQuantityChangedListener listener;

    public CartAdapter(Context context, List<CartItem> cartItems, OnQuantityChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvDesc, tvPrice, tvQuantity;
        Button btnMinus, btnPlus;

        public CartViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgCartItem);
            tvName = itemView.findViewById(R.id.tvCartName);
            tvDesc = itemView.findViewById(R.id.tvCartDesc);
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        // Load ảnh bằng URL
        Picasso.get().load(item.getImageUrl()).into(holder.imgProduct);

        holder.tvName.setText(item.getName());
        holder.tvDesc.setText(item.getDesc());
        holder.tvPrice.setText(item.getPrice() + "đ");
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                listener.onQuantityChanged();
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            listener.onQuantityChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}

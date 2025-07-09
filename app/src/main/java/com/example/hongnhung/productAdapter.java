
package com.example.hongnhung;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    private Context context;
    private List<products> sanPhamList;

    public productAdapter(Context context, List<products> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        products sp = sanPhamList.get(position);
        holder.txtTen.setText(sp.getTitle());
        holder.txtGia.setText(sp.getPrice() + " $");
        Picasso.get().load(sp.getImage()).into(holder.imgSanPham);

        // Xử lý click để mở chi tiết sản phẩm
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", sp.getTitle());
            intent.putExtra("desc", "Mô tả sản phẩm đang cập nhật...");
            intent.putExtra("price", sp.getPrice() + " $");
            intent.putExtra("pricesale", sp.getPrice() + " $");
            intent.putExtra("imageUrl", sp.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView txtTen, txtGia;

        public ViewHolder(View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtGia = itemView.findViewById(R.id.txtGia);
        }
    }
}

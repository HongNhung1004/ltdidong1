package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView tvProductName, tvProductDesc, tvProductPrice,tvProductPriceSale;
    Button btnBuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvProductPrice = findViewById(R.id.tvOriginalPrice);
        tvProductPriceSale = findViewById(R.id.tvSalePrice);


        btnBuyNow = findViewById(R.id.btnBuyNow);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            startActivity(intent);
            // kết thúc CheckoutActivity
        });
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        String price = intent.getStringExtra("price");
        String pricesale = intent.getStringExtra("pricesale");
        int imageRes = intent.getIntExtra("image", R.drawable.banner_sample);

        tvProductName.setText(name);
        tvProductDesc.setText(desc);
        TextView tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        imgProduct.setImageResource(imageRes);
        btnBuyNow.setOnClickListener(v -> {
            Intent checkoutIntent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
            checkoutIntent.putExtra("name", name);
            checkoutIntent.putExtra("desc", desc);
            checkoutIntent.putExtra("price", Integer.parseInt(pricesale.replace(".", "").replace("đ", "").trim())); // Lấy giá giảm
            checkoutIntent.putExtra("quantity", 1);
            checkoutIntent.putExtra("image", imageRes);
            checkoutIntent.putExtra("address", "Chưa nhập");
            startActivity(checkoutIntent);
        });


        Toast.makeText(this, "Mua " + name, Toast.LENGTH_SHORT).show();

        TextView tvCartCount = findViewById(R.id.tvCartCount);
        int cartCount = CartManager.getInstance().getTotalQuantity();
        if (cartCount > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(cartCount));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }

        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(v -> {
            Intent Cartintent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(Cartintent);
        });
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            String query = textView.getText().toString();
            Toast.makeText(this, "Tìm kiếm: " + query, Toast.LENGTH_SHORT).show();
            return true;
        });


        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {
            CartItem item = new CartItem(imageRes, name, desc, 1,
                    Integer.parseInt(pricesale.replace(".", "").replace("đ", "").trim()));
            CartManager.getInstance().addItem(item);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            finish(); // quay lại HomeActivity
        });


        btnAddToCart.setOnClickListener(v -> {
            // Thêm vào CartManager
            CartItem item = new CartItem(imageRes, name, desc, 1, Integer.parseInt(pricesale.replace(".", "").replace("đ", "").trim()));
            CartManager.getInstance().addItem(item);

            // Hiển thị thông báo
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            // Cập nhật số lượng trên icon nếu có (nếu dùng TextView nhỏ hiển thị số)
            TextView tvBadge = findViewById(R.id.tvCartCount);
            if (tvBadge != null) {
                int count = CartManager.getInstance().getTotalQuantity();
                tvBadge.setText(String.valueOf(count));
                tvBadge.setVisibility(View.VISIBLE);
            }
        });


    }
}

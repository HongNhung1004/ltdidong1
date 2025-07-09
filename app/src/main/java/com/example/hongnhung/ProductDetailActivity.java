
package com.example.hongnhung;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct, cartIcon;
    TextView tvProductName, tvProductDesc, tvOriginalPrice, tvSalePrice, tvCartCount;
    EditText etSearch;
    Button btnBuyNow, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvSalePrice = findViewById(R.id.tvSalePrice);
        tvCartCount = findViewById(R.id.tvCartCount);
        etSearch = findViewById(R.id.etSearch);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        cartIcon = findViewById(R.id.cartIcon);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Nhận dữ liệu
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        String price = intent.getStringExtra("price");
        String pricesale = intent.getStringExtra("pricesale");
        String imageUrl = intent.getStringExtra("imageUrl");

        // Hiển thị dữ liệu
        tvProductName.setText(name);
        tvProductDesc.setText(desc);
        tvOriginalPrice.setText(price);
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvSalePrice.setText(pricesale);
        Picasso.get().load(imageUrl).into(imgProduct);

        // Sự kiện back
        btnBack.setOnClickListener(v -> finish());

        // Mua ngay
        btnBuyNow.setOnClickListener(v -> {
            Intent checkoutIntent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
            checkoutIntent.putExtra("name", name);
            checkoutIntent.putExtra("desc", desc);
            checkoutIntent.putExtra("price", pricesale);
            checkoutIntent.putExtra("quantity", 1);
            checkoutIntent.putExtra("imageUrl", imageUrl);
            startActivity(checkoutIntent);
        });

        // Tìm kiếm
        etSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            String query = textView.getText().toString();
            Toast.makeText(this, "Tìm kiếm: " + query, Toast.LENGTH_SHORT).show();
            return true;
        });

        // Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            CartItem item = new CartItem(imageUrl, name, desc, 1, pricesale);
            CartManager.getInstance().addItem(item);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            updateCartBadge();
        });

        // Giỏ hàng
        cartIcon.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        updateCartBadge();
    }

    private void updateCartBadge() {
        int count = CartManager.getInstance().getTotalQuantity();
        if (count > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(count));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }
    }
}

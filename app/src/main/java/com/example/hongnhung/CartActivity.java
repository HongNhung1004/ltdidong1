
package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCartItems;
    EditText etAddress;
    TextView tvSubtotal, tvShippingFee, tvDiscount, tvTotal;
    Button btnCheckout;

    List<CartItem> cartItems;
    CartAdapter adapter;

    final int SHIPPING_FEE = 20000;
    final int DISCOUNT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo View
        etAddress = findViewById(R.id.etAddress);
        rvCartItems = findViewById(R.id.rvCartItems);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShippingFee = findViewById(R.id.tvShippingFee);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        ImageButton btnBackDetail = findViewById(R.id.btnBackDetail);

        // Lấy giỏ hàng
        cartItems = CartManager.getInstance().getCartItems();

        // Trở về trang chi tiết sản phẩm đầu tiên
        btnBackDetail.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                CartItem item = cartItems.get(0);
                Intent intent = new Intent(CartActivity.this, ProductDetailActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("desc", item.getDesc());
                intent.putExtra("price", item.getPrice()); // giá gốc
                intent.putExtra("pricesale", item.getPrice()); // giá sale (giống giá gốc nếu chưa có khác biệt)
                intent.putExtra("imageUrl", item.getImageUrl());
                startActivity(intent);
                finish();
            }
        });


        // Adapter
        adapter = new CartAdapter(this, cartItems, this::updateSummary);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        updateSummary();

        // Thanh toán
        btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                CartItem item = cartItems.get(0);
                Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                checkoutIntent.putExtra("name", item.getName());
                checkoutIntent.putExtra("desc", item.getDesc());
                checkoutIntent.putExtra("price", item.getPrice());
                checkoutIntent.putExtra("quantity", item.getQuantity());
                checkoutIntent.putExtra("imageUrl", item.getImageUrl());
                checkoutIntent.putExtra("address", etAddress.getText().toString());
                startActivity(checkoutIntent);
            } else {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateSummary() {
        int subtotal = 0;
        for (CartItem item : cartItems) {
            try {
                int priceInt = Integer.parseInt(item.getPrice().replace("đ", "").replace(".", "").trim());
                subtotal += priceInt * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        tvSubtotal.setText("Tạm tính: " + subtotal + "đ");
        tvShippingFee.setText("Phí vận chuyển: " + SHIPPING_FEE + "đ");
        tvDiscount.setText("Giảm giá: " + DISCOUNT + "đ");
        tvTotal.setText("Tổng cộng: " + (subtotal + SHIPPING_FEE - DISCOUNT) + "đ");
    }

}

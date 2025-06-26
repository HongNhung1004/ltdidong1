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
        btnBackDetail.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, ProductDetailActivity.class);


            intent.putExtra("name", cartItems.get(0).name);
            intent.putExtra("desc", cartItems.get(0).description);
            intent.putExtra("price", cartItems.get(0).price);
            intent.putExtra("image", cartItems.get(0).imageRes);

            startActivity(intent);
            finish(); // kết thúc CartActivity
        });


        cartItems = CartManager.getInstance().getCartItems(); // ✅ đúng


        // Adapter
        adapter = new CartAdapter(this, cartItems, this::updateSummary);
        rvCartItems.setAdapter(adapter);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        updateSummary();

        // Nút thanh toán
        btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                CartItem item = cartItems.get(0); // lấy sản phẩm đầu tiên (hoặc có thể gửi danh sách)
                Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                checkoutIntent.putExtra("name", item.name);
                checkoutIntent.putExtra("desc", item.description);
                checkoutIntent.putExtra("price", item.price);
                checkoutIntent.putExtra("quantity", item.quantity);
                checkoutIntent.putExtra("image", item.imageRes);
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
            subtotal += item.price * item.quantity;
        }

        tvSubtotal.setText("Tạm tính: " + subtotal + "đ");
        tvShippingFee.setText("Phí vận chuyển: " + SHIPPING_FEE + "đ");
        tvDiscount.setText("Giảm giá: " + DISCOUNT + "đ");
        tvTotal.setText("Tổng cộng: " + (subtotal + SHIPPING_FEE - DISCOUNT) + "đ");
    }
}


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
            finish(); // Luôn quay lại màn hình trước (ProductDetailActivity hoặc HomeActivity)
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

    //    private void updateSummary() {
//        int subtotal = 0;
//        for (CartItem item : cartItems) {
//            try {
//                int priceInt = Integer.parseInt(item.getPrice().replace("đ", "").replace(".", "").trim());
//                subtotal += priceInt * item.getQuantity();
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
    private double parsePrice(String priceString) {
        try {
            priceString = priceString.replaceAll("[^\\d.]", "");
            return Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void updateSummary() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += parsePrice(item.getPrice()) * item.getQuantity();
        }

        tvSubtotal.setText("Tạm tính: $" + String.format("%.2f", subtotal));
        tvShippingFee.setText("Phí vận chuyển: Free");
        tvDiscount.setText("Giảm giá: $0.00");

        double total = subtotal; // vì freeship, no discount
        tvTotal.setText("Tổng cộng: $" + String.format("%.2f", total));
    }

}
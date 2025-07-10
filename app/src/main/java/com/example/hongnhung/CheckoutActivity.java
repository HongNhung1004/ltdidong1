package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    TextView tvAddress, tvSubtotal, tvShipping, tvTotal, btnEditAddress;
    Button btnPlaceOrder;
    ImageButton btnBackCart;
    LinearLayout productListLayout;
    final int SHIPPING_FEE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvAddress = findViewById(R.id.tvAddress);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnBackCart = findViewById(R.id.btnBackCart);
        btnEditAddress = findViewById(R.id.btnEditAddress);
        productListLayout = findViewById(R.id.productListLayout);

        String address = getIntent().getStringExtra("address");
        tvAddress.setText(address != null && !address.isEmpty() ? address : "Chưa nhập");

        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Không có sản phẩm trong giỏ!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        double subtotal = 0;

        for (CartItem item : cartItems) {
            View itemView = inflater.inflate(R.layout.item_checkout_product, productListLayout, false);
            ImageView img = itemView.findViewById(R.id.imgItemProduct);
            TextView tvName = itemView.findViewById(R.id.tvItemName);
            TextView tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
            TextView tvPrice = itemView.findViewById(R.id.tvItemPrice);

            tvName.setText(item.getName());
            tvQuantity.setText("Số lượng: " + item.getQuantity());
            tvPrice.setText("Giá: " + item.getPrice());

            if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                Picasso.get().load(item.getImageUrl()).into(img);
            } else {
                img.setImageResource(R.drawable.banner_sample);
            }

            try {
                double price = Double.parseDouble(item.getPrice().replaceAll("[^\\d.]", ""));
                subtotal += price * item.getQuantity();
            } catch (Exception e) {
                e.printStackTrace();
            }

            productListLayout.addView(itemView);
        }

        double total = subtotal + SHIPPING_FEE;
        tvSubtotal.setText("Tạm tính: $" + String.format("%.2f", subtotal));
        tvShipping.setText("Phí vận chuyển: Miễn phí");
        tvTotal.setText("Tổng cộng: $" + String.format("%.2f", total));

        btnBackCart.setOnClickListener(v -> finish());

        btnPlaceOrder.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            CartManager.getInstance().clear();
            Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnEditAddress.setOnClickListener(v -> showEditAddressDialog());
    }

    private void showEditAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa địa chỉ");

        final EditText input = new EditText(this);
        input.setHint("Nhập địa chỉ mới...");
        input.setText(tvAddress.getText().toString());
        input.setPadding(24, 24, 24, 24);

        builder.setView(input);
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newAddress = input.getText().toString().trim();
            if (!newAddress.isEmpty()) {
                tvAddress.setText(newAddress);
            } else {
                Toast.makeText(this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

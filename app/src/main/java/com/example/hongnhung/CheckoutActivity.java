package com.example.hongnhung;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    TextView tvAddress, tvProductName, tvQuantity, tvPrice, tvSubtotal, tvShipping, tvTotal;
    ImageView imgProduct;
    Button btnPlaceOrder;
    ImageButton btnBackCart;
    final int SHIPPING_FEE = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Ánh xạ view
        tvAddress = findViewById(R.id.tvAddress);
        tvProductName = findViewById(R.id.tvProductName);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.tvPrice);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        imgProduct = findViewById(R.id.imgProduct);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Nhận dữ liệu từ intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");

        int price = intent.getIntExtra("price", 0);
        int quantity = intent.getIntExtra("quantity", 1);
        int imageRes = intent.getIntExtra("image", R.drawable.banner_sample);
        String address = intent.getStringExtra("address");

        // Tính toán
        int subtotal = price * quantity;
        int total = subtotal + SHIPPING_FEE;

        // Hiển thị dữ liệu
        tvAddress.setText(address != null ? address : "Chưa nhập");
        tvProductName.setText(name);
        tvQuantity.setText("Số lượng: " + quantity);
        tvPrice.setText("Giá: " + price + "đ");
        imgProduct.setImageResource(imageRes);
        tvSubtotal.setText("Tạm tính: " + subtotal + "đ");
        tvShipping.setText("Phí vận chuyển: " + SHIPPING_FEE + "đ");
        tvTotal.setText("Tổng cộng: " + total + "đ");
        btnBackCart = findViewById(R.id.btnBackCart); // ánh xạ nút quay lại

        //Xử lý nút quay lại giỏ hàng
        btnBackCart.setOnClickListener(v -> {
            Intent Cartintent = new Intent(CheckoutActivity.this, CartActivity.class);
            startActivity(Cartintent);
            // kết thúc CheckoutActivity
        });

        // Nút đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển về trang Home
            Intent Homeintent = new Intent(CheckoutActivity.this, HomeActivity.class);
            Homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Homeintent);

            finish(); // kết thúc CheckoutActivity
        });

    }
}

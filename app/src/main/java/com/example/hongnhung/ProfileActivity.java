package com.example.hongnhung;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView textName, textEmail, textPhone;
    ImageView imageAvatar;
    Button btnEdit, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ view
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textPhone);
        imageAvatar = findViewById(R.id.imageAvatar);
        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);

        // Gán dữ liệu mẫu (có thể lấy từ SharedPreferences hoặc Firebase)
        textName.setText("Trần Thị Hồng Nhung");
        textEmail.setText("nhung@example.com");
        textPhone.setText("0987654321");

        // Nút chỉnh sửa
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng chỉnh sửa đang phát triển", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, EditProfileActivity.class));
        });

        // Nút đăng xuất
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            // Ví dụ: Xóa session, rồi quay lại màn hình đăng nhập
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        ImageButton btnBackHome;

        btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(v -> finish()); // Quay về màn hình trước

    }
}

package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText edtPhone, edtPass, edtConfirm;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtPhone = findViewById(R.id.editTextPhone);
        edtPass = findViewById(R.id.editTextPassword);
        edtConfirm = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        ImageButton btnBack = findViewById(R.id.btnBackToLogin);
        btnBack.setOnClickListener(v -> finish()); // Hoặc quay về LoginActivity

        btnRegister.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString();
            String pass = edtPass.getText().toString();
            String confirm = edtConfirm.getText().toString();

            if (phone.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu thông tin vào SharedPreferences hoặc gửi lên server...
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish(); // hoặc chuyển sang trang Login
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

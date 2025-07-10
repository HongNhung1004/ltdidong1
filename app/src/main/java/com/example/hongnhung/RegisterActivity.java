//package com.example.hongnhung;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class RegisterActivity extends AppCompatActivity {
//    EditText edtPhone, edtPass, edtConfirm;
//    Button btnRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        edtPhone = findViewById(R.id.editTextPhone);
//        edtPass = findViewById(R.id.editTextPassword);
//        edtConfirm = findViewById(R.id.editTextConfirmPassword);
//        btnRegister = findViewById(R.id.btnRegister);
//
//        ImageButton btnBack = findViewById(R.id.btnBackToLogin);
//        btnBack.setOnClickListener(v -> finish()); // Hoặc quay về LoginActivity
//
//        btnRegister.setOnClickListener(v -> {
//            String phone = edtPhone.getText().toString();
//            String pass = edtPass.getText().toString();
//            String confirm = edtConfirm.getText().toString();
//
//            if (phone.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
//                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (!pass.equals(confirm)) {
//                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Lưu thông tin vào SharedPreferences hoặc gửi lên server...
//            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
//            finish(); // hoặc chuyển sang trang Login
//        });
//
//        btnBack.setOnClickListener(v -> {
//            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        });
//    }
//}
package com.example.hongnhung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextPhone, editTextPassword, editTextConfirmPassword;
    Button btnRegister;
    ImageButton btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // XML bạn cung cấp

        // Ánh xạ view
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        // Sự kiện nút Đăng ký
        btnRegister.setOnClickListener(view -> {
            String phone = editTextPhone.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Kiểm tra dữ liệu
            if (phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu thông tin vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("registered_username", phone);
            editor.putString("registered_password", password);
            editor.apply();

            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            // Quay lại màn hình đăng nhập
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Sự kiện nút quay lại
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

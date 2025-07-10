//
//package com.example.hongnhung;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class LoginActivity extends AppCompatActivity {
//
//    EditText objPhone, objPass;
//    Button btnLogin, btnRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        objPhone = findViewById(R.id.editTextPhone);
//        objPass = findViewById(R.id.editTextNumberPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        btnRegister = findViewById(R.id.btnRegister);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txtPhone = objPhone.getText().toString().trim();
//                String txtPass = objPass.getText().toString().trim();
//
//                if (txtPhone.isEmpty() || txtPass.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Gọi API để kiểm tra user
//                String url = "https://fakestoreapi.com/users";
//
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//
//                JsonArrayRequest request = new JsonArrayRequest(
//                        Request.Method.GET,
//                        url,
//                        null,
//                        new Response.Listener<JSONArray>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                boolean loginSuccess = false;
//                                for (int i = 0; i < response.length(); i++) {
//                                    try {
//                                        JSONObject user = response.getJSONObject(i);
//                                        JSONObject name = user.getJSONObject("name");
//
//                                        String username = user.getString("username"); // Dùng username như "phone"
//                                        String password = user.getString("password");
//
//                                        if (username.equals(txtPhone) && password.equals(txtPass)) {
//                                            loginSuccess = true;
//                                            break;
//                                        }
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                if (loginSuccess) {
//                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                                    Intent it = new Intent(getApplicationContext(), HomeActivity.class);
//                                    startActivity(it);
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(LoginActivity.this, "Lỗi kết nối đến API", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                );
//
//                queue.add(request);
//            }
//        });
//
//        btnRegister.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//            startActivity(intent);
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextPhone, editTextNumberPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // XML layout bạn cung cấp

        // Ánh xạ view
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumberPassword = findViewById(R.id.editTextNumberPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(view -> {
            String phone = editTextPhone.getText().toString().trim();
            String password = editTextNumberPassword.getText().toString().trim();

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE);
            String registeredPhone = prefs.getString("registered_username", null);
            String registeredPassword = prefs.getString("registered_password", null);

            if (phone.equals(registeredPhone) && password.equals(registeredPassword)) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình chính
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển đến màn hình đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

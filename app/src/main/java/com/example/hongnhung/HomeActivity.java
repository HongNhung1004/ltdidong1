
package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ImageView btnProfile, cartIcon;
    private TextView tvCartCount;
    private EditText etSearch;
    private RecyclerView recyclerView;
    private List<products> sanPhamList;
    private productAdapter productAdapter;

    private final String url = "https://fakestoreapi.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo danh sách
        sanPhamList = new ArrayList<>();

        // Ánh xạ View
        tvCartCount = findViewById(R.id.tvCartCount);
        btnProfile = findViewById(R.id.btnProfile);
        cartIcon = findViewById(R.id.cartIcon);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.recyclerView);

        // Khởi tạo RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new productAdapter(this, sanPhamList);
        recyclerView.setAdapter(productAdapter);

        // Gọi API để lấy dữ liệu sản phẩm
        loadSanPham();

        // Xử lý tìm kiếm
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String query = etSearch.getText().toString();
            Toast.makeText(this, "Đang tìm: " + query, Toast.LENGTH_SHORT).show();
            return true;
        });

        // Sự kiện chuyển trang
        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        cartIcon.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
    }

    private void loadSanPham() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    sanPhamList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            products sp = new products(
                                    obj.getInt("id"),
                                    obj.getString("title"),
                                    obj.getDouble("price"),
                                    obj.getString("image")
                            );
                            sanPhamList.add(sp);
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", e.getMessage());
                        }
                    }
                    productAdapter.notifyDataSetChanged();
                    Log.d("API", "Tổng số sản phẩm: " + sanPhamList.size());

                },
                error -> Log.e("API_ERROR", error.toString())

        );

        queue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

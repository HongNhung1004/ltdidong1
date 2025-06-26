package com.example.hongnhung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    ImageView btnProfile;
    TextView tvCartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCartCount = findViewById(R.id.tvCartCount);

        btnProfile = findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        ImageView ivCartIcon = findViewById(R.id.cartIcon);
       ivCartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
           startActivity(intent);
      });

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String query = etSearch.getText().toString();
            Toast.makeText(this, "Đang tìm: " + query, Toast.LENGTH_SHORT).show();
            return true;
        });

        // Danh sách sản phẩm
        String[] names = {
                "Áo thun nữ", "Váy mùa hè", "Mũ Thời Trang",
                "Giày thể thao nữ", "Túi xách thời trang", "Quần jean", "Áo Polo Nam"
        };
        String[] descriptions = {
                "Áo thun chất liệu cotton thoáng mát", "Váy mùa hè xinh xắn", "Mũ thời trang cá tính",
                "Giày thể thao nữ siêu nhẹ", "Túi xách thời trang cao cấp", "Quần jean", "Áo polo nam siêu sang trọng"
        };
        String[] prices = {
                "129.000đ", "249.000đ", "299.000đ", "499.000đ", "399.000đ", "59.000đ", "189.000đ"
        };
        String[] pricesale = {
                "100.000đ", "200.000đ", "249.000đ", "405.000đ", "349.000đ", "45.000đ", "149.000đ"
        };

        int[] images = {
                R.drawable.pants, R.drawable.dress, R.drawable.hat,
                R.drawable.dress, R.drawable.bag, R.drawable.pants, R.drawable.tshirt
        };

        ListView listView = findViewById(R.id.categoryListView);
        CategoryAdapter adapter = new CategoryAdapter(this, names, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("name", names[position]);
            intent.putExtra("desc", descriptions[position]);
            intent.putExtra("price", prices[position]);
            intent.putExtra("pricesale", pricesale[position]);
            intent.putExtra("image", images[position]);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void updateCartBadge() {
        TextView badge = findViewById(R.id.tvCartCount);
        int count = CartManager.getInstance().getTotalQuantity();
        if (count > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(count));
        } else {
            badge.setVisibility(View.GONE);
        }
    }

}

package com.example.codeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.codeassistant.databinding.ActivityMainBinding;
import com.example.codeassistant.model.Banner;
import com.example.codeassistant.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化 Banner 列表
        loadBanners();

        // 导航按钮
        binding.btnChat.setOnClickListener(v -> startActivity(new Intent(this, SmartChatActivity.class)));
        binding.btnMemo.setOnClickListener(v -> startActivity(new Intent(this, MemoListActivity.class)));
        binding.btnLogout.setOnClickListener(v -> {
            // 逻辑：清除 Token 并跳转回 LoginActivity
        });
    }

    private void loadBanners() {
        RetrofitClient.getInstance().create(com.example.codeassistant.network.ApiService.class)
                .getBanners()
                .enqueue(new Callback<List<Banner>>() {
                    @Override
                    public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // 这里需要实现一个 BannerAdapter 来显示图片
                            // 暂时简单打印日志或使用 Toast
                            Toast.makeText(MainActivity.this, "Banner 加载成功: " + response.body().size(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Banner>> call, Throwable t) {
                        // Handle error
                    }
                });
    }
}
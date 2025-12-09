package com.example.codeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codeassistant.databinding.ActivityMainBinding;
import com.example.codeassistant.model.Banner;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import com.example.codeassistant.util.DataStoreUtil; // 确保导入正确
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BannerAdapter bannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. 初始化 Banner
        initBanner();

        // 2. 加载 Banner 数据
        loadBanners();

        // 3. 设置按钮监听
        // "开始练习" 对应后端的 Chat 功能
        binding.btnChat.setOnClickListener(v -> startActivity(new Intent(this, SmartChatActivity.class)));

        binding.btnMemo.setOnClickListener(v -> startActivity(new Intent(this, MemoListActivity.class)));

        binding.btnLogout.setOnClickListener(v -> {
            // 清除 Token (简单的做法是存空字符串，严谨做法是 remove key)
            DataStoreUtil.saveToken("").subscribe();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void initBanner() {
        bannerAdapter = new BannerAdapter(new ArrayList<>());
        binding.vpBanner.setAdapter(bannerAdapter);
    }

    private void loadBanners() {
        RetrofitClient.getInstance().create(ApiService.class)
                .getBanners()
                .enqueue(new Callback<List<Banner>>() {
                    @Override
                    public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Banner> banners = response.body();
                            bannerAdapter.setBanners(banners);
                        } else {
                            Toast.makeText(MainActivity.this, "Banner 加载失败: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Banner>> call, Throwable t) {
                        // 网络错误时不阻断用户使用其他功能，仅打印日志或Toast
                        Toast.makeText(MainActivity.this, "无法连接服务器加载 Banner", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
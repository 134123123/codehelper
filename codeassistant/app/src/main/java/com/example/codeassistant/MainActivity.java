package com.example.codeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codeassistant.databinding.ActivityMainBinding;
import com.example.codeassistant.model.Banner;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import com.example.codeassistant.util.DataStoreUtil;
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

        // 1. 设置 Banner
        bannerAdapter = new BannerAdapter(new ArrayList<>());
        binding.vpBanner.setAdapter(bannerAdapter);
        loadBanners();

        // 2. 设置卡片点击事件
        binding.cardChat.setOnClickListener(v -> startActivity(new Intent(this, SmartChatActivity.class)));

        // 跳转到新的练习页面
        binding.cardExercise.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));

        binding.cardMemo.setOnClickListener(v -> startActivity(new Intent(this, MemoListActivity.class)));

        binding.cardLogout.setOnClickListener(v -> {
            DataStoreUtil.saveToken("").subscribe();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void loadBanners() {
        RetrofitClient.getInstance().create(ApiService.class)
                .getBanners()
                .enqueue(new Callback<List<Banner>>() {
                    @Override
                    public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            bannerAdapter.setBanners(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Banner>> call, Throwable t) {
                        // 静默失败或简单的 Log
                    }
                });
    }
}
package com.example.codeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codeassistant.databinding.ActivityLoginBinding;
import com.example.codeassistant.model.UserLoginDTO;
import com.example.codeassistant.network.ApiConfig;
import com.example.codeassistant.network.RetrofitClient;
import com.example.codeassistant.util.DataStoreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            login(username, password);
        });

        // 可以在这里添加跳往注册页面的逻辑
    }

    private void login(String username, String password) {
        UserLoginDTO loginDTO = new UserLoginDTO(username, password);
        RetrofitClient.getInstance().create(com.example.codeassistant.network.ApiService.class)
                .login(loginDTO)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String token = response.body();
                            // 保存 Token 到 DataStore
                            DataStoreUtil.saveToken(token).subscribe();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
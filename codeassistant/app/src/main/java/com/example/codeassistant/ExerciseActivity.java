package com.example.codeassistant;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codeassistant.databinding.ActivityExerciseBinding;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseActivity extends AppCompatActivity {
    private ActivityExerciseBinding binding;
    private ApiService apiService;
    private String currentQuestion = ""; // 暂存当前题目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        binding.btnGenerate.setOnClickListener(v -> {
            String type = binding.etType.getText().toString();
            if (type.isEmpty()) type = "Java基础";
            generateQuestion(type);
        });

        // 【修复2：这里改成 btnCheck】
        binding.btnCheck.setOnClickListener(v -> checkAnswer());
    }


    private void generateQuestion(String type) {
        binding.tvQuestion.setText("AI 正在思考出题中...");
        binding.tvAnalysis.setVisibility(View.GONE);

        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("difficulty", "中等");

        apiService.generateExercise(params).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentQuestion = response.body();
                    binding.tvQuestion.setText(currentQuestion);
                } else {
                    binding.tvQuestion.setText("出题失败: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tvQuestion.setText("网络错误: " + t.getMessage());
            }
        });
    }

    private void checkAnswer() {
        String answer = binding.etAnswer.getText().toString();
        if (answer.isEmpty()) {
            Toast.makeText(this, "请先输入你的答案", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.tvAnalysis.setVisibility(View.VISIBLE);
        binding.tvAnalysis.setText("AI 老师正在阅卷...");

        Map<String, String> params = new HashMap<>();
        params.put("question", currentQuestion);
        params.put("answer", answer);

        apiService.checkExercise(params).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.tvAnalysis.setText(response.body());
                } else {
                    binding.tvAnalysis.setText("判卷失败");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tvAnalysis.setText("网络错误");
            }
        });
    }
}
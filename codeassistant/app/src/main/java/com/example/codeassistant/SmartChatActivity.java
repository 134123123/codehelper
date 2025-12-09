package com.example.codeassistant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.codeassistant.databinding.ActivitySmartChatBinding;
import com.example.codeassistant.model.ImageAnalysisVO;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartChatActivity extends AppCompatActivity {
    private ActivitySmartChatBinding binding;
    private ApiService apiService;
    private String uploadedImageUrl = null; // 存储上传后的图片 URL

    // 图片选择器
    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    binding.ivSelectedImage.setVisibility(View.VISIBLE);
                    // 使用 Glide 加载图片预览
                    Glide.with(this).load(uri).into(binding.ivSelectedImage);
                    // 立即上传
                    uploadImage(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 ViewBinding
        binding = ActivitySmartChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // 按钮事件
        binding.btnSelectImage.setOnClickListener(v -> pickImage.launch("image/*"));

        binding.btnSend.setOnClickListener(v -> {
            String question = binding.etQuestion.getText().toString();
            if (question.isEmpty()) {
                Toast.makeText(this, "请输入问题", Toast.LENGTH_SHORT).show();
                return;
            }
            // 判断是纯文本还是带图提问
            if (uploadedImageUrl != null) {
                analyzeImage(uploadedImageUrl, question);
            } else {
                askText(question);
            }
        });
    }

    // --- 核心修复：添加 uploadImage 方法 ---
    private void uploadImage(Uri uri) {
        File file = uriToFile(uri);
        if (file == null) {
            Toast.makeText(this, "文件转换失败", Toast.LENGTH_SHORT).show();
            return;
        }

        // 构造请求体
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        binding.tvResult.setText("正在上传图片...");

        apiService.uploadImage(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    uploadedImageUrl = response.body();
                    binding.tvResult.setText("图片上传成功！\n可以输入问题并发送了。");
                    Toast.makeText(SmartChatActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    binding.tvResult.setText("上传失败: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tvResult.setText("上传出错: " + t.getMessage());
            }
        });
    }

    // --- 核心修复：添加 uriToFile 辅助方法 ---
    private File uriToFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) return null;

            File tempFile = File.createTempFile("upload", ".jpg", getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- 核心修复：添加 askText 方法 ---
    private void askText(String question) {
        Map<String, String> body = new HashMap<>();
        body.put("question", question);

        binding.tvResult.setText("思考中...");
        apiService.askText(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    binding.tvResult.setText(response.body());
                } else {
                    binding.tvResult.setText("请求失败: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tvResult.setText("网络错误: " + t.getMessage());
            }
        });
    }

    // --- 核心修复：添加 analyzeImage 方法 ---
    private void analyzeImage(String imageUrl, String question) {
        Map<String, String> body = new HashMap<>();
        body.put("imageUrl", imageUrl);
        body.put("question", question);

        binding.tvResult.setText("正在分析图片...");
        apiService.analyzeImage(body).enqueue(new Callback<ImageAnalysisVO>() {
            @Override
            public void onResponse(Call<ImageAnalysisVO> call, Response<ImageAnalysisVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.tvResult.setText(response.body().getAnswer());
                    // 分析完后重置图片，以便下次可以纯文本提问
                    uploadedImageUrl = null;
                    binding.ivSelectedImage.setVisibility(View.GONE);
                } else {
                    binding.tvResult.setText("分析失败: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ImageAnalysisVO> call, Throwable t) {
                binding.tvResult.setText("网络错误: " + t.getMessage());
            }
        });
    }
}
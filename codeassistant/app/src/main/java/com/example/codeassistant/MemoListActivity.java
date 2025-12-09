package com.example.codeassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.codeassistant.databinding.ActivityMemoListBinding;
import com.example.codeassistant.model.Memo;
import com.example.codeassistant.model.MemoAddDTO;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoListActivity extends AppCompatActivity {
    private ActivityMemoListBinding binding;
    private ApiService apiService;
    private MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // 初始化 RecyclerView 和 Adapter
        binding.rvMemos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemoAdapter(memo -> deleteMemo(memo.getId())); // 处理删除回调
        binding.rvMemos.setAdapter(adapter);

        loadMemos();

        binding.fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadMemos() {
        apiService.getMemos().enqueue(new Callback<List<Memo>>() {
            @Override
            public void onResponse(Call<List<Memo>> call, Response<List<Memo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setNewData(response.body());
                } else {
                    Toast.makeText(MemoListActivity.this, "加载失败: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Memo>> call, Throwable t) {
                Toast.makeText(MemoListActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_memo, null);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);

        new AlertDialog.Builder(this)
                .setTitle("新建备忘录")
                .setView(view)
                .setPositiveButton("保存", (dialog, which) -> {
                    String title = etTitle.getText().toString();
                    String content = etContent.getText().toString();
                    if (!title.isEmpty()) {
                        createMemo(new MemoAddDTO(title, content));
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void createMemo(MemoAddDTO dto) {
        apiService.addMemo(dto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MemoListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    loadMemos(); // 刷新列表
                } else {
                    Toast.makeText(MemoListActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MemoListActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMemo(Long id) {
        apiService.deleteMemo(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MemoListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    loadMemos();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MemoListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
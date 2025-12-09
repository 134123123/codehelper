package com.example.codeassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.codeassistant.databinding.ActivityMemoListBinding;
import com.example.codeassistant.model.Memo;
import com.example.codeassistant.model.MemoAddDTO;
import com.example.codeassistant.network.ApiService;
import com.example.codeassistant.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoListActivity extends AppCompatActivity {
    private ActivityMemoListBinding binding;
    private ApiService apiService;
    // 需要实现一个简单的 RecyclerView Adapter
    // private MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        binding.rvMemos.setLayoutManager(new LinearLayoutManager(this));
        // adapter = new MemoAdapter(new ArrayList<>());
        // binding.rvMemos.setAdapter(adapter);

        loadMemos();

        binding.fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadMemos() {
        apiService.getMemos().enqueue(new Callback<List<Memo>>() {
            @Override
            public void onResponse(Call<List<Memo>> call, Response<List<Memo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // adapter.setNewData(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Memo>> call, Throwable t) {}
        });
    }

    private void showAddDialog() {
        android.view.View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_memo, null);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);

        new AlertDialog.Builder(this)
                .setTitle("新建备忘录")
                .setView(view)
                .setPositiveButton("保存", (dialog, which) -> {
                    MemoAddDTO dto = new MemoAddDTO(etTitle.getText().toString(), etContent.getText().toString());
                    createMemo(dto);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void createMemo(MemoAddDTO dto) {
        apiService.addMemo(dto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    loadMemos(); // 刷新列表
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }
}
package com.example.codeassistant;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.codeassistant.databinding.ItemMemoBinding; // 需创建对应布局
import com.example.codeassistant.model.Memo;
import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {
    private List<Memo> memoList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Memo memo);
        // 可以扩展 onEditClick
    }

    public MemoAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setNewData(List<Memo> list) {
        this.memoList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMemoBinding binding = ItemMemoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MemoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        holder.binding.tvTitle.setText(memo.getTitle());
        holder.binding.tvContent.setText(memo.getContent());
        holder.binding.tvTime.setText(memo.getCreateTime());

        holder.binding.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(memo);
        });
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    static class MemoViewHolder extends RecyclerView.ViewHolder {
        ItemMemoBinding binding;
        public MemoViewHolder(ItemMemoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
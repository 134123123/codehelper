package com.example.codeassistant;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.codeassistant.databinding.ItemBannerBinding;
import com.example.codeassistant.model.Banner;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners;

    public BannerAdapter(List<Banner> banners) {
        this.banners = banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 使用 ViewBinding 加载布局
        ItemBannerBinding binding = ItemBannerBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Banner banner = banners.get(position);
        // 使用 Glide 加载网络图片
        Glide.with(holder.itemView.getContext())
                .load(banner.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // 占位图
                .error(R.drawable.ic_launcher_background)       // 错误图
                .into(holder.binding.ivBanner);

        // 如果有点击事件（比如跳转网页），可以在这里处理
        // holder.itemView.setOnClickListener(...)
    }

    @Override
    public int getItemCount() {
        return banners == null ? 0 : banners.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding binding;
        public BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
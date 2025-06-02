package com.example.sngnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sngnews.R;
import com.example.sngnews.databinding.NewLayoutBinding;
import com.example.sngnews.models.NewsModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModel> news;
    private OnClick listener;
    public NewsAdapter(List<NewsModel> news, OnClick listener) {
        this.news = news;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewLayoutBinding binding = NewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel model = news.get(position);
        holder.bind(model);
        holder.binding.newsEyeBtn.setOnClickListener(v -> {
            listener.clickEye(model.getId());
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.clickMain(model.getLink());
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface OnClick{
        void clickMain(String link);
        void clickEye(int id);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private NewLayoutBinding binding;

        public ViewHolder(NewLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsModel model){
            binding.newsSubtitle.setText(model.getDescription());
            binding.newsTitle.setText(model.getTitle());
        }
    }
}

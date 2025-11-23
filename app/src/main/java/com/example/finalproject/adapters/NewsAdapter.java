package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.NewsArticle;
import com.example.finalproject.utils.TimeUtils;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsArticle> articles;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NewsArticle article);
    }

    public NewsAdapter(List<NewsArticle> articles, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView articleImage;
        private TextView articleTitle;
        private TextView articleSource;
        private TextView articleTime;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            articleImage = itemView.findViewById(R.id.articleImage);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleSource = itemView.findViewById(R.id.articleSource);
            articleTime = itemView.findViewById(R.id.articleTime);

            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(articles.get(getAdapterPosition()));
                }
            });
        }

        public void bind(NewsArticle article) {
            articleTitle.setText(article.getTitle());
            articleSource.setText(article.getSourceName());
            
            if (article.getPublishedAt() != null) {
                articleTime.setText(TimeUtils.getTimeAgo(article.getPublishedAt()));
            }

            if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(article.getUrlToImage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(articleImage);
            }
        }
    }
}


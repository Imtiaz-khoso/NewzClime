package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.utils.Constants;

public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.SourceViewHolder> {
    private OnSourceClickListener listener;

    public interface OnSourceClickListener {
        void onSourceClick(String source);
    }

    public NewsSourceAdapter(OnSourceClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_source, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceViewHolder holder, int position) {
        holder.bind(Constants.NEWS_SOURCES[position]);
    }

    @Override
    public int getItemCount() {
        return Constants.NEWS_SOURCES.length;
    }

    class SourceViewHolder extends RecyclerView.ViewHolder {
        private TextView sourceName;

        public SourceViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceName = itemView.findViewById(R.id.sourceName);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSourceClick(Constants.NEWS_SOURCES[getAdapterPosition()]);
                }
            });
        }

        public void bind(String source) {
            sourceName.setText(source);
        }
    }
}



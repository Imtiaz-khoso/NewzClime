package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.utils.Constants;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private int selectedPosition = 0;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }

    public CategoryAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(Constants.CATEGORIES[position], position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return Constants.CATEGORIES.length;
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(selectedPosition);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                setSelectedPosition(position);
                if (listener != null) {
                    listener.onCategoryClick(Constants.CATEGORIES[position]);
                }
            });
        }

        public void bind(String category, boolean isSelected) {
            categoryName.setText(category);
            categoryName.setSelected(isSelected);
            itemView.setSelected(isSelected);
            
            if (isSelected) {
                categoryName.setTextColor(itemView.getContext().getColor(R.color.category_text_selected));
                itemView.setBackgroundColor(itemView.getContext().getColor(R.color.category_selected));
            } else {
                categoryName.setTextColor(itemView.getContext().getColor(R.color.category_text_unselected));
                itemView.setBackgroundColor(itemView.getContext().getColor(R.color.category_unselected));
            }
        }
    }
}


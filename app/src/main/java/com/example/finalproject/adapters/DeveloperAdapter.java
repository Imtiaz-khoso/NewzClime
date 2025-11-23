package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.Developer;

import java.util.List;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder> {
    private List<Developer> developers;

    public DeveloperAdapter(List<Developer> developers) {
        this.developers = developers;
    }

    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_developer, parent, false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperViewHolder holder, int position) {
        Developer developer = developers.get(position);
        holder.bind(developer);
    }

    @Override
    public int getItemCount() {
        return developers != null ? developers.size() : 0;
    }

    class DeveloperViewHolder extends RecyclerView.ViewHolder {
        private ImageView developerImage;
        private TextView developerName;
        private TextView developerReg;
        private TextView developerRole;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);
            developerImage = itemView.findViewById(R.id.developerImage);
            developerName = itemView.findViewById(R.id.developerName);
            developerReg = itemView.findViewById(R.id.developerReg);
            developerRole = itemView.findViewById(R.id.developerRole);
        }

        public void bind(Developer developer) {
            developerName.setText(developer.getName());
            developerReg.setText("Registration: " + developer.getRegistrationNumber());
            developerRole.setText("Role: " + developer.getRole());
            
            // Set image if available, otherwise show placeholder
            developerImage.setVisibility(View.VISIBLE);
            if (developer.getImageResource() != 0) {
                Glide.with(itemView.getContext())
                        .load(developer.getImageResource())
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(developerImage);
            } else {
                // For developers without images, show a default placeholder
                developerImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }
    }
}



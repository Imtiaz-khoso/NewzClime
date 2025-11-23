package com.example.finalproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapters.DeveloperAdapter;
import com.example.finalproject.models.Developer;

import java.util.ArrayList;
import java.util.List;

public class DevelopersActivity extends AppCompatActivity {
    private RecyclerView developersRecyclerView;
    private DeveloperAdapter developerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();
        loadDevelopers();
    }

    private void initializeViews() {
        developersRecyclerView = findViewById(R.id.developersRecyclerView);
    }

    private void setupRecyclerView() {
        List<Developer> developers = new ArrayList<>();
        developerAdapter = new DeveloperAdapter(developers);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        developersRecyclerView.setLayoutManager(layoutManager);
        developersRecyclerView.setAdapter(developerAdapter);
    }

    private void setupClickListeners() {
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void loadDevelopers() {
        List<Developer> developers = new ArrayList<>();
        
        // IMTIAZ ALI - Group Leader (with image)
        developers.add(new Developer("IMTIAZ ALI", "2k22-IT-70", "Group Leader, Backend Developer, API Configuration", R.drawable.imtiaz));
        
        // Anika Joyo - UI Front End Developer (with placeholder image - girl)
        developers.add(new Developer("Anika Joyo", "2k22-IT-03", "UI Front End Developer", R.drawable.girl_placeholder));
        
        // Arslan - UI Backend Helper (with image) - Show before Nimra
        developers.add(new Developer("Arslan", "2k22-IT-41", "UI Backend Helper", R.drawable.arslan));
        
        // Nimra Saeed - UI Designer (with placeholder image - girl)
        developers.add(new Developer("Nimra Saeed", "2k22-IT-94", "UI Designer (Low Fidelity)", R.drawable.girl_placeholder));
        
        // NadaR HuSSaIN - Front End Helper (with image)
        developers.add(new Developer("NadaR HuSSaIN", "2k22-IT-118", "Front End Helper", R.drawable.nadar));

        developerAdapter = new DeveloperAdapter(developers);
        developersRecyclerView.setAdapter(developerAdapter);
    }
}


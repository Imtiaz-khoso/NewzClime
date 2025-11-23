package com.example.finalproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapters.CategoryAdapter;
import com.example.finalproject.adapters.NewsAdapter;
import com.example.finalproject.api.ApiClient;
import com.example.finalproject.api.NewsApiService;
import com.example.finalproject.models.NewsArticle;
import com.example.finalproject.models.NewsResponse;
import com.example.finalproject.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakingNewsActivity extends AppCompatActivity {
    private RecyclerView categoriesRecyclerView;
    private RecyclerView featuredNewsRecyclerView;
    private RecyclerView breakingNewsRecyclerView;
    private RecyclerView latestNewsRecyclerView;
    
    private CategoryAdapter categoryAdapter;
    private NewsAdapter featuredAdapter;
    private NewsAdapter breakingNewsAdapter;
    private NewsAdapter latestNewsAdapter;
    
    private NewsApiService newsApiService;
    private List<NewsArticle> featuredArticles;
    private List<NewsArticle> breakingNewsArticles;
    private List<NewsArticle> latestArticles;
    
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaking_news);

        initializeViews();
        setupRecyclerViews();
        setupClickListeners();
        setupSearch();
        
        newsApiService = ApiClient.getNewsRetrofitInstance().create(NewsApiService.class);
        loadNews();
    }

    private void initializeViews() {
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        featuredNewsRecyclerView = findViewById(R.id.featuredNewsRecyclerView);
        breakingNewsRecyclerView = findViewById(R.id.breakingNewsRecyclerView);
        latestNewsRecyclerView = findViewById(R.id.latestNewsRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
    }

    private void setupRecyclerViews() {
        // Categories
        categoryAdapter = new CategoryAdapter(category -> {
            loadNewsByCategory(category);
        });
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecyclerView.setAdapter(categoryAdapter);

        // Featured News
        featuredArticles = new ArrayList<>();
        featuredAdapter = new NewsAdapter(featuredArticles, article -> {
            openArticleDetail(article);
        });
        featuredNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredNewsRecyclerView.setAdapter(featuredAdapter);

        // Breaking News
        breakingNewsArticles = new ArrayList<>();
        breakingNewsAdapter = new NewsAdapter(breakingNewsArticles, article -> {
            openArticleDetail(article);
        });
        breakingNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        breakingNewsRecyclerView.setAdapter(breakingNewsAdapter);

        // Latest News
        latestArticles = new ArrayList<>();
        latestNewsAdapter = new NewsAdapter(latestArticles, article -> {
            openArticleDetail(article);
        });
        latestNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        latestNewsRecyclerView.setAdapter(latestNewsAdapter);
    }

    private void setupClickListeners() {
        ImageView backButton = findViewById(R.id.backButton);
        ImageView filterButton = findViewById(R.id.filterButton);
        TextView viewMoreBreaking = findViewById(R.id.viewMoreBreaking);

        backButton.setOnClickListener(v -> finish());

        filterButton.setOnClickListener(v -> {
            // Show filter dialog
        });
    }

    private void setupSearch() {
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchEditText.getText().toString().trim();
            if (query.length() > 0) {
                searchNews(query);
                return true;
            }
            return false;
        });
        
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.length() > 2) {
                    searchNews(query);
                } else if (query.length() == 0) {
                    loadNews();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadNews() {
        // Use 'everything' endpoint as top-headlines is blocked
        Call<NewsResponse> call = newsApiService.getEverything("news", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> articles = response.body().getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        // Featured (first 3)
                        featuredArticles.clear();
                        featuredArticles.addAll(articles.subList(0, Math.min(3, articles.size())));
                        featuredAdapter.notifyDataSetChanged();

                        // Breaking (next 5)
                        breakingNewsArticles.clear();
                        if (articles.size() > 3) {
                            breakingNewsArticles.addAll(articles.subList(3, Math.min(8, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();

                        // Latest (rest)
                        latestArticles.clear();
                        if (articles.size() > 8) {
                            latestArticles.addAll(articles.subList(8, Math.min(20, articles.size())));
                        }
                        latestNewsAdapter.notifyDataSetChanged();
                    } else {
                        // Try fallback with Pakistan query
                        loadNewsFallback();
                    }
                } else {
                    // Try fallback
                    loadNewsFallback();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                loadNewsFallback();
            }
        });
    }
    
    private void loadNewsFallback() {
        Call<NewsResponse> call = newsApiService.getEverything("pakistan", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> articles = response.body().getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        featuredArticles.clear();
                        featuredArticles.addAll(articles.subList(0, Math.min(3, articles.size())));
                        featuredAdapter.notifyDataSetChanged();

                        breakingNewsArticles.clear();
                        if (articles.size() > 3) {
                            breakingNewsArticles.addAll(articles.subList(3, Math.min(8, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();

                        latestArticles.clear();
                        if (articles.size() > 8) {
                            latestArticles.addAll(articles.subList(8, Math.min(20, articles.size())));
                        }
                        latestNewsAdapter.notifyDataSetChanged();
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(BreakingNewsActivity.this, "No articles available. Please try again later.", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(BreakingNewsActivity.this, "Failed to load news. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(BreakingNewsActivity.this, "Failed to load news. Check internet connection.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadNewsByCategory(String category) {
        String query = category.toLowerCase();
        if (query.equals("trending")) {
            query = "news";
        } else if (query.equals("politics")) {
            query = "politics OR political";
        } else if (query.equals("sports")) {
            query = "sports OR football OR cricket";
        } else if (query.equals("health")) {
            query = "health OR medical";
        } else if (query.equals("technology")) {
            query = "technology OR tech";
        } else if (query.equals("business")) {
            query = "business OR economy";
        }
        
        Call<NewsResponse> call = newsApiService.getEverything(query, Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> articles = response.body().getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        breakingNewsArticles.clear();
                        breakingNewsArticles.addAll(articles.subList(0, Math.min(15, articles.size())));
                        breakingNewsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(BreakingNewsActivity.this, "Failed to load " + category + " news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchNews(String query) {
        if (query == null || query.trim().isEmpty()) {
            loadNews();
            return;
        }
        
        Call<NewsResponse> call = newsApiService.getEverything(query.trim(), Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> articles = response.body().getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        // Clear all sections
                        featuredArticles.clear();
                        featuredAdapter.notifyDataSetChanged();
                        
                        // Show search results in breaking news section
                        breakingNewsArticles.clear();
                        breakingNewsArticles.addAll(articles.subList(0, Math.min(20, articles.size())));
                        breakingNewsAdapter.notifyDataSetChanged();
                        
                        // Clear latest news
                        latestArticles.clear();
                        latestNewsAdapter.notifyDataSetChanged();
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(BreakingNewsActivity.this, "No articles found for: " + query, Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(BreakingNewsActivity.this, "Search failed. Please try again.", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(BreakingNewsActivity.this, "Search failed. Check internet connection.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


    private void openArticleDetail(NewsArticle article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }
}


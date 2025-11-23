package com.example.finalproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.adapters.CategoryAdapter;
import com.example.finalproject.adapters.NewsAdapter;
import com.example.finalproject.adapters.NewsSourceAdapter;
import com.example.finalproject.api.ApiClient;
import com.example.finalproject.api.NewsApiService;
import com.example.finalproject.api.WeatherApiService;
import com.example.finalproject.models.NewsArticle;
import com.example.finalproject.models.NewsResponse;
import com.example.finalproject.models.WeatherResponse;
import com.example.finalproject.utils.Constants;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView newsSourcesRecyclerView;
    private RecyclerView trendingRecyclerView;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView breakingNewsRecyclerView;
    
    private NewsSourceAdapter newsSourceAdapter;
    private NewsAdapter trendingAdapter;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter breakingNewsAdapter;
    
    private NewsApiService newsApiService;
    private WeatherApiService weatherApiService;
    private List<NewsArticle> trendingArticles;
    private List<NewsArticle> breakingNewsArticles;
    
    // Weather widget views
    private TextView weatherCity, weatherTemp, weatherDesc, weatherHumidity, weatherWind;
    private ImageView weatherIconSmall;
    
    // Shimmer views
    private View shimmerTrendingContainer;
    private View shimmerBreakingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setupRecyclerViews();
        setupBottomNavigation();
        setupClickListeners();
        
        newsApiService = ApiClient.getNewsRetrofitInstance().create(NewsApiService.class);
        weatherApiService = ApiClient.getWeatherRetrofitInstance().create(WeatherApiService.class);
        showShimmer();
        loadNews();
        loadWeather();
    }

    private void initializeViews() {
        newsSourcesRecyclerView = findViewById(R.id.newsSourcesRecyclerView);
        trendingRecyclerView = findViewById(R.id.trendingRecyclerView);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        breakingNewsRecyclerView = findViewById(R.id.breakingNewsRecyclerView);
        
        // Shimmer containers
        shimmerTrendingContainer = findViewById(R.id.shimmerTrendingContainer);
        shimmerBreakingContainer = findViewById(R.id.shimmerBreakingContainer);
        
        // Make logo circular
        ImageView profileImage = findViewById(R.id.profileImage);
        if (profileImage != null) {
            profileImage.setOutlineProvider(new android.view.ViewOutlineProvider() {
                @Override
                public void getOutline(android.view.View view, android.graphics.Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            profileImage.setClipToOutline(true);
        }
        
        // Weather widget views
        weatherCity = findViewById(R.id.weatherCity);
        weatherTemp = findViewById(R.id.weatherTemp);
        weatherDesc = findViewById(R.id.weatherDesc);
        weatherHumidity = findViewById(R.id.weatherHumidity);
        weatherWind = findViewById(R.id.weatherWind);
        weatherIconSmall = findViewById(R.id.weatherIconSmall);
    }

    private void setupRecyclerViews() {
        // News Sources
        newsSourceAdapter = new NewsSourceAdapter(source -> {
            // Handle source click
        });
        newsSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newsSourcesRecyclerView.setAdapter(newsSourceAdapter);

        // Trending
        trendingArticles = new ArrayList<>();
        trendingAdapter = new NewsAdapter(trendingArticles, article -> {
            openArticleDetail(article);
        });
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(trendingAdapter);

        // Categories
        categoryAdapter = new CategoryAdapter(category -> {
            loadNewsByCategory(category);
        });
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecyclerView.setAdapter(categoryAdapter);

        // Breaking News
        breakingNewsArticles = new ArrayList<>();
        breakingNewsAdapter = new NewsAdapter(breakingNewsArticles, article -> {
            openArticleDetail(article);
        });
        breakingNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        breakingNewsRecyclerView.setAdapter(breakingNewsAdapter);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_explore) {
                startActivity(new Intent(this, BreakingNewsActivity.class));
                return true;
            } else if (itemId == R.id.nav_weather) {
                startActivity(new Intent(this, WeatherActivity.class));
                return true;
            } else if (itemId == R.id.nav_developers) {
                startActivity(new Intent(this, DevelopersActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupClickListeners() {
        TextView viewMoreTrending = findViewById(R.id.viewMoreTrending);
        TextView viewMoreBreaking = findViewById(R.id.viewMoreBreaking);
        ImageView searchIcon = findViewById(R.id.searchIcon);

        viewMoreTrending.setOnClickListener(v -> {
            startActivity(new Intent(this, BreakingNewsActivity.class));
        });

        viewMoreBreaking.setOnClickListener(v -> {
            startActivity(new Intent(this, BreakingNewsActivity.class));
        });

        searchIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, BreakingNewsActivity.class));
        });
    }

    private void loadNews() {
        Log.d("HomeActivity", "Loading news from API using 'everything' endpoint...");
        // Use 'everything' endpoint directly as top-headlines is blocked by Cloudflare
        // Try Pakistan news first
        Call<NewsResponse> call = newsApiService.getEverything("pakistan", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d("HomeActivity", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    Log.d("HomeActivity", "Status: " + newsResponse.getStatus());
                    Log.d("HomeActivity", "Total results: " + newsResponse.getTotalResults());
                    
                    List<NewsArticle> articles = newsResponse.getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        Log.d("HomeActivity", "Got " + articles.size() + " articles");
                        hideShimmer();
                        // Set trending (first 5)
                        trendingArticles.clear();
                        trendingArticles.addAll(articles.subList(0, Math.min(5, articles.size())));
                        trendingAdapter.notifyDataSetChanged();

                        // Set breaking news (rest)
                        breakingNewsArticles.clear();
                        if (articles.size() > 5) {
                            breakingNewsArticles.addAll(articles.subList(5, Math.min(15, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();
                        Log.d("HomeActivity", "News loaded successfully!");
                    } else {
                        Log.d("HomeActivity", "No articles in response, trying world news...");
                        // Try world news as fallback
                        loadWorldNews();
                    }
                } else {
                    Log.e("HomeActivity", "Response not successful. Code: " + response.code());
                    // Try world news as fallback
                    loadWorldNews();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("HomeActivity", "API call failed", t);
                t.printStackTrace();
                // Try world news as fallback
                loadWorldNews();
            }
        });
    }

    private void loadWorldNews() {
        Log.d("HomeActivity", "Trying world news as fallback using 'everything' endpoint...");
        // Use 'everything' endpoint with 'news' query
        Call<NewsResponse> call = newsApiService.getEverything("news", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d("HomeActivity", "World news response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    Log.d("HomeActivity", "World news status: " + newsResponse.getStatus());
                    Log.d("HomeActivity", "World news total results: " + newsResponse.getTotalResults());
                    
                    List<NewsArticle> articles = newsResponse.getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        Log.d("HomeActivity", "Got " + articles.size() + " world news articles");
                        hideShimmer();
                        trendingArticles.clear();
                        trendingArticles.addAll(articles.subList(0, Math.min(5, articles.size())));
                        trendingAdapter.notifyDataSetChanged();

                        breakingNewsArticles.clear();
                        if (articles.size() > 5) {
                            breakingNewsArticles.addAll(articles.subList(5, Math.min(15, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();
                        Log.d("HomeActivity", "World news loaded successfully!");
                    } else {
                        Log.w("HomeActivity", "No world news articles");
                        runOnUiThread(() -> {
                            Toast.makeText(HomeActivity.this, "No news articles available. Please try again later.", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    Log.e("HomeActivity", "World news response not successful. Code: " + response.code());
                    runOnUiThread(() -> {
                        Toast.makeText(HomeActivity.this, "Failed to load news. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("HomeActivity", "World news API call failed", t);
                t.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Failed to load news. Check internet connection.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadEverythingNews() {
        Log.d("HomeActivity", "Trying 'everything' endpoint with query 'pakistan'...");
        // Try everything endpoint with Pakistan query
        Call<NewsResponse> call = newsApiService.getEverything("pakistan", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d("HomeActivity", "Everything response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    Log.d("HomeActivity", "Everything status: " + newsResponse.getStatus());
                    Log.d("HomeActivity", "Everything total results: " + newsResponse.getTotalResults());
                    
                    List<NewsArticle> articles = newsResponse.getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        Log.d("HomeActivity", "Got " + articles.size() + " articles from everything endpoint");
                        trendingArticles.clear();
                        trendingArticles.addAll(articles.subList(0, Math.min(5, articles.size())));
                        trendingAdapter.notifyDataSetChanged();

                        breakingNewsArticles.clear();
                        if (articles.size() > 5) {
                            breakingNewsArticles.addAll(articles.subList(5, Math.min(15, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();
                        Log.d("HomeActivity", "Everything news loaded successfully!");
                    } else {
                        Log.w("HomeActivity", "No articles from everything endpoint, trying 'news' query...");
                        loadGeneralNews();
                    }
                } else {
                    Log.e("HomeActivity", "Everything response not successful. Code: " + response.code());
                    loadGeneralNews();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("HomeActivity", "Everything API call failed", t);
                loadGeneralNews();
            }
        });
    }

    private void loadGeneralNews() {
        Log.d("HomeActivity", "Trying 'everything' endpoint with query 'news'...");
        // Try everything endpoint with general news query
        Call<NewsResponse> call = newsApiService.getEverything("news", Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.d("HomeActivity", "General news response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    List<NewsArticle> articles = newsResponse.getArticles();
                    if (articles != null && !articles.isEmpty()) {
                        Log.d("HomeActivity", "Got " + articles.size() + " general news articles");
                        trendingArticles.clear();
                        trendingArticles.addAll(articles.subList(0, Math.min(5, articles.size())));
                        trendingAdapter.notifyDataSetChanged();

                        breakingNewsArticles.clear();
                        if (articles.size() > 5) {
                            breakingNewsArticles.addAll(articles.subList(5, Math.min(15, articles.size())));
                        }
                        breakingNewsAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("HomeActivity", "No articles found");
                        runOnUiThread(() -> {
                            Toast.makeText(HomeActivity.this, "No news articles available. Please try again later.", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    Log.e("HomeActivity", "General news response not successful");
                    runOnUiThread(() -> {
                        Toast.makeText(HomeActivity.this, "Failed to load news. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("HomeActivity", "General news API call failed", t);
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Failed to load news. Check internet connection.", Toast.LENGTH_SHORT).show();
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
        
        // Show shimmer
        showShimmer();
        
        Call<NewsResponse> call = newsApiService.getEverything(query, Constants.NEWS_API_KEY, "publishedAt");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                hideShimmer();
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
                hideShimmer();
                Toast.makeText(HomeActivity.this, "Failed to load " + category + " news", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showShimmer() {
        runOnUiThread(() -> {
            if (shimmerTrendingContainer != null) shimmerTrendingContainer.setVisibility(View.VISIBLE);
            if (shimmerBreakingContainer != null) shimmerBreakingContainer.setVisibility(View.VISIBLE);
            if (trendingRecyclerView != null) trendingRecyclerView.setVisibility(View.GONE);
            if (breakingNewsRecyclerView != null) breakingNewsRecyclerView.setVisibility(View.GONE);
        });
    }
    
    private void hideShimmer() {
        runOnUiThread(() -> {
            if (shimmerTrendingContainer != null) shimmerTrendingContainer.setVisibility(View.GONE);
            if (shimmerBreakingContainer != null) shimmerBreakingContainer.setVisibility(View.GONE);
            if (trendingRecyclerView != null) trendingRecyclerView.setVisibility(View.VISIBLE);
            if (breakingNewsRecyclerView != null) breakingNewsRecyclerView.setVisibility(View.VISIBLE);
        });
    }


    private void loadWeather() {
        if (weatherCity == null || weatherApiService == null) return;
        
        Call<WeatherResponse> call = weatherApiService.getCurrentWeather(
                "Karachi,Pakistan",
                Constants.WEATHER_API_KEY,
                "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    updateWeatherWidget(weatherResponse);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Keep default values or show error
            }
        });
    }

    private void updateWeatherWidget(WeatherResponse response) {
        if (weatherCity == null) return;
        
        if (response.getCityName() != null) {
            weatherCity.setText(response.getCityName() + ", Pakistan");
        }

        if (response.getMain() != null) {
            int temp = (int) Math.round(response.getMain().getTemp());
            weatherTemp.setText(temp + "°C");
            weatherHumidity.setText((int) response.getMain().getHumidity() + "%");
        }

        if (response.getWind() != null) {
            weatherWind.setText(String.format(Locale.getDefault(), "%.1f km/h", response.getWind().getSpeed() * 3.6));
        }

        if (response.getWeather() != null && !response.getWeather().isEmpty()) {
            WeatherResponse.WeatherInfo weatherInfo = response.getWeather().get(0);
            weatherDesc.setText(capitalize(weatherInfo.getDescription()));
            
            String iconCode = weatherInfo.getIcon();
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            Glide.with(this)
                    .load(iconUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(weatherIconSmall);
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void openArticleDetail(NewsArticle article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }
}


package com.example.finalproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.example.finalproject.adapters.WeatherForecastAdapter;
import com.example.finalproject.api.ApiClient;
import com.example.finalproject.api.WeatherApiService;
import com.example.finalproject.models.WeatherForecastResponse;
import com.example.finalproject.models.WeatherResponse;
import com.example.finalproject.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView currentDate;
    private TextView temperature;
    private TextView weatherDescription;
    private TextView humidity;
    private TextView windSpeed;
    private ImageView weatherIcon;
    private RecyclerView forecastRecyclerView;
    
    private WeatherApiService weatherApiService;
    private WeatherForecastAdapter forecastAdapter;
    private String defaultCity = "Karachi,Pakistan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initializeViews();
        setupClickListeners();
        setupRecyclerView();
        
        weatherApiService = ApiClient.getWeatherRetrofitInstance().create(WeatherApiService.class);
        loadWeatherData();
    }

    private void initializeViews() {
        cityName = findViewById(R.id.cityName);
        currentDate = findViewById(R.id.currentDate);
        temperature = findViewById(R.id.temperature);
        weatherDescription = findViewById(R.id.weatherDescription);
        humidity = findViewById(R.id.humidity);
        windSpeed = findViewById(R.id.windSpeed);
        weatherIcon = findViewById(R.id.weatherIcon);
        forecastRecyclerView = findViewById(R.id.forecastRecyclerView);
    }

    private void setupClickListeners() {
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        forecastAdapter = new WeatherForecastAdapter();
        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forecastRecyclerView.setAdapter(forecastAdapter);
    }

    private void loadWeatherData() {
        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        currentDate.setText(sdf.format(new Date()));

        // Load current weather
        if (Constants.WEATHER_API_KEY.equals("YOUR_WEATHER_API_KEY_HERE")) {
            // Use demo data if API key not set
            loadDemoData();
        } else {
            loadCurrentWeather();
            loadWeatherForecast();
        }
    }

    private void loadCurrentWeather() {
        Call<WeatherResponse> call = weatherApiService.getCurrentWeather(
                defaultCity, 
                Constants.WEATHER_API_KEY, 
                "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    updateCurrentWeather(weatherResponse);
                } else {
                    Toast.makeText(WeatherActivity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show();
                    loadDemoData();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                loadDemoData();
            }
        });
    }

    private void loadWeatherForecast() {
        Call<WeatherForecastResponse> call = weatherApiService.getWeatherForecast(
                defaultCity,
                Constants.WEATHER_API_KEY,
                "metric"
        );

        call.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherForecastResponse forecastResponse = response.body();
                    if (forecastResponse.getList() != null) {
                        // Get daily forecasts (one per day, 5 days)
                        List<WeatherForecastResponse.ForecastItem> dailyForecasts = getDailyForecasts(forecastResponse.getList());
                        forecastAdapter.setForecastItems(dailyForecasts);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                // Handle error silently, demo data already loaded
            }
        });
    }

    private List<WeatherForecastResponse.ForecastItem> getDailyForecasts(List<WeatherForecastResponse.ForecastItem> list) {
        List<WeatherForecastResponse.ForecastItem> dailyForecasts = new ArrayList<>();
        if (list == null || list.isEmpty()) return dailyForecasts;

        // Get forecasts for next 5 days (one per day at 12:00)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        
        for (int i = 0; i < list.size() && dailyForecasts.size() < 5; i++) {
            WeatherForecastResponse.ForecastItem item = list.get(i);
            if (item.getDateText() != null && item.getDateText().contains("12:00:00")) {
                dailyForecasts.add(item);
            }
        }

        // If we don't have enough 12:00 forecasts, take first 5 unique days
        if (dailyForecasts.size() < 5) {
            dailyForecasts.clear();
            String lastDate = "";
            for (WeatherForecastResponse.ForecastItem item : list) {
                if (item.getDateText() != null) {
                    String itemDate = item.getDateText().split(" ")[0];
                    if (!itemDate.equals(lastDate) && dailyForecasts.size() < 5) {
                        dailyForecasts.add(item);
                        lastDate = itemDate;
                    }
                }
            }
        }

        return dailyForecasts;
    }

    private void updateCurrentWeather(WeatherResponse response) {
        if (response.getCityName() != null) {
            cityName.setText(response.getCityName() + ", Pakistan");
        }

        if (response.getMain() != null) {
            int temp = (int) Math.round(response.getMain().getTemp());
            temperature.setText(temp + "°C");
            humidity.setText((int) response.getMain().getHumidity() + "%");
        }

        if (response.getWind() != null) {
            windSpeed.setText(String.format(Locale.getDefault(), "%.1f km/h", response.getWind().getSpeed() * 3.6));
        }

        if (response.getWeather() != null && !response.getWeather().isEmpty()) {
            WeatherResponse.WeatherInfo weatherInfo = response.getWeather().get(0);
            weatherDescription.setText(capitalize(weatherInfo.getDescription()));
            
            // Load weather icon
            String iconCode = weatherInfo.getIcon();
            String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            Glide.with(this)
                    .load(iconUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(weatherIcon);
        }
    }

    private void loadDemoData() {
        cityName.setText("Karachi, Pakistan");
        temperature.setText("28°C");
        weatherDescription.setText("Partly Cloudy");
        humidity.setText("65%");
        windSpeed.setText("15 km/h");
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}


package com.example.finalproject.api;

import com.example.finalproject.models.WeatherResponse;
import com.example.finalproject.models.WeatherForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    // Current Weather Data API
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    // 5 Day / 3 Hour Forecast API
    @GET("forecast")
    Call<WeatherForecastResponse> getWeatherForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}



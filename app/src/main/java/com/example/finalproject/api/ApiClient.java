package com.example.finalproject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/";
    private static final String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    
    private static Retrofit newsRetrofit;
    private static Retrofit weatherRetrofit;

    public static Retrofit getNewsRetrofitInstance() {
        if (newsRetrofit == null) {
            newsRetrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return newsRetrofit;
    }

    public static Retrofit getWeatherRetrofitInstance() {
        if (weatherRetrofit == null) {
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherRetrofit;
    }
}



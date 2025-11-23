package com.example.finalproject.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherForecastResponse {
    @SerializedName("list")
    private List<ForecastItem> list;
    
    @SerializedName("city")
    private City city;

    public List<ForecastItem> getList() {
        return list;
    }

    public void setList(List<ForecastItem> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public static class ForecastItem {
        @SerializedName("dt")
        private long timestamp;
        
        @SerializedName("main")
        private WeatherResponse.Main main;
        
        @SerializedName("weather")
        private List<WeatherResponse.WeatherInfo> weather;
        
        @SerializedName("dt_txt")
        private String dateText;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public WeatherResponse.Main getMain() {
            return main;
        }

        public void setMain(WeatherResponse.Main main) {
            this.main = main;
        }

        public List<WeatherResponse.WeatherInfo> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherResponse.WeatherInfo> weather) {
            this.weather = weather;
        }

        public String getDateText() {
            return dateText;
        }

        public void setDateText(String dateText) {
            this.dateText = dateText;
        }
    }

    public static class City {
        @SerializedName("name")
        private String name;
        
        @SerializedName("country")
        private String country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}



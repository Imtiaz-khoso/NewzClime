package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.WeatherForecastResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ForecastViewHolder> {
    private List<WeatherForecastResponse.ForecastItem> forecastItems;

    public WeatherForecastAdapter() {
        this.forecastItems = new ArrayList<>();
    }

    public void setForecastItems(List<WeatherForecastResponse.ForecastItem> items) {
        this.forecastItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        WeatherForecastResponse.ForecastItem item = forecastItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return forecastItems != null ? forecastItems.size() : 0;
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView forecastDate;
        private ImageView forecastIcon;
        private TextView forecastTemp;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            forecastDate = itemView.findViewById(R.id.forecastDate);
            forecastIcon = itemView.findViewById(R.id.forecastIcon);
            forecastTemp = itemView.findViewById(R.id.forecastTemp);
        }

        public void bind(WeatherForecastResponse.ForecastItem item) {
            // Format date
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd", Locale.getDefault());
            String dateStr = sdf.format(new Date(item.getTimestamp() * 1000));
            forecastDate.setText(dateStr);

            // Set temperature
            if (item.getMain() != null) {
                int temp = (int) Math.round(item.getMain().getTemp());
                forecastTemp.setText(temp + "°C");
            }

            // Load weather icon
            if (item.getWeather() != null && !item.getWeather().isEmpty()) {
                String iconCode = item.getWeather().get(0).getIcon();
                String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                Glide.with(itemView.getContext())
                        .load(iconUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(forecastIcon);
            }
        }
    }
}



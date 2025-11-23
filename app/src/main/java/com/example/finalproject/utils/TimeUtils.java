package com.example.finalproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final String INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final SimpleDateFormat inputFormatter = new SimpleDateFormat(INPUT_FORMAT, Locale.getDefault());

    public static String getTimeAgo(String publishedAt) {
        try {
            Date publishedDate = inputFormatter.parse(publishedAt);
            if (publishedDate == null) {
                return "Just now";
            }

            long now = System.currentTimeMillis();
            long diff = now - publishedDate.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);

            if (seconds < 60) {
                return "Just now";
            } else if (minutes < 60) {
                return minutes + " min ago";
            } else if (hours < 24) {
                return hours + "h ago";
            } else if (days < 7) {
                return days + " days ago";
            } else {
                SimpleDateFormat outputFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                return outputFormatter.format(publishedDate);
            }
        } catch (ParseException e) {
            return "Just now";
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return formatter.format(date);
    }
}



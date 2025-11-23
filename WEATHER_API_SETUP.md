# Weather API Setup Guide

## Which API to Choose?

For your project, you should use the **FREE tier** which includes:

1. **Current Weather Data API** ✅ (Free)
   - Access current weather for any location
   - Included in free subscription
   - Perfect for showing current temperature, humidity, wind speed

2. **5 Day / 3 Hour Forecast API** ✅ (Free)
   - 5-day weather forecast
   - 3-hour step intervals
   - Included in free subscription
   - Perfect for showing 5-day forecast

**You DON'T need to pay for Professional Collections!** The free tier is sufficient for your project.

## Step-by-Step: Get Your Free API Key

### Step 1: Sign Up
1. Go to [OpenWeatherMap.org](https://openweathermap.org/api)
2. Click **"Sign Up"** or **"Sign In"** if you already have an account
3. Create a free account (no credit card required)

### Step 2: Get Your API Key
1. After signing up, you'll be redirected to your account dashboard
2. Go to **"API Keys"** section (usually in the top navigation)
3. You'll see a default API key, or you can create a new one
4. **Copy your API key** (it looks like: `abc123def456ghi789jkl012mno345pq`)

### Step 3: Add API Key to Your App
1. Open: `app/src/main/java/com/example/finalproject/utils/Constants.java`
2. Find: `public static final String WEATHER_API_KEY = "YOUR_WEATHER_API_KEY_HERE";`
3. Replace `YOUR_WEATHER_API_KEY_HERE` with your actual API key
4. Example: `public static final String WEATHER_API_KEY = "abc123def456ghi789jkl012mno345pq";`

## Free Tier Limits

- **1,000 API calls per day** (more than enough for testing)
- **60 calls per minute**
- **1,000,000 calls per month**

This is perfect for development and testing!

## What the App Will Do

Once you add your API key:

1. **Current Weather Screen** will show:
   - City name (default: Karachi, Pakistan)
   - Current temperature in Celsius
   - Weather description (e.g., "Partly Cloudy")
   - Humidity percentage
   - Wind speed in km/h
   - Weather icon

2. **5-Day Forecast** will show:
   - Next 5 days
   - Daily temperature
   - Weather icons for each day

## API Endpoints Used

The app uses these two free endpoints:

1. **Current Weather**: 
   ```
   https://api.openweathermap.org/data/2.5/weather?q=Karachi,Pakistan&appid=YOUR_KEY&units=metric
   ```

2. **5-Day Forecast**:
   ```
   https://api.openweathermap.org/data/2.5/forecast?q=Karachi,Pakistan&appid=YOUR_KEY&units=metric
   ```

## Testing Without API Key

If you don't add an API key yet, the app will show **demo data** so you can test the UI. Once you add your API key, it will automatically switch to real weather data.

## Troubleshooting

### API Key Not Working?
- Make sure you copied the entire key (no spaces)
- Wait a few minutes after creating the key (activation delay)
- Check your account dashboard to verify the key is active

### Getting 401 Unauthorized Error?
- Your API key might be incorrect
- Check if the key is activated in your dashboard

### No Weather Data Showing?
- Check your internet connection
- Verify API key is correct
- Check Logcat for error messages

## Need Help?

- OpenWeatherMap Support: https://openweathermap.org/faq
- API Documentation: https://openweathermap.org/api

---

**Remember**: The free tier is perfect for your project. You don't need to pay for anything!



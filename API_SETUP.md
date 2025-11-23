# API Setup Instructions

This application requires API keys for News and Weather services. Follow the steps below to get your API keys and configure the application.

## 1. News API Setup

### Step 1: Get News API Key
1. Visit [NewsAPI.org](https://newsapi.org/)
2. Click on "Get API Key" or "Sign Up"
3. Create a free account (free tier allows 100 requests per day)
4. After registration, you'll receive your API key

### Step 2: Configure News API Key
1. Open the file: `app/src/main/java/com/example/finalproject/utils/Constants.java`
2. Find the line: `public static final String NEWS_API_KEY = "YOUR_NEWS_API_KEY_HERE";`
3. Replace `YOUR_NEWS_API_KEY_HERE` with your actual API key
4. Example: `public static final String NEWS_API_KEY = "abc123def456ghi789";`

### News API Endpoints Used:
- **Top Headlines**: `https://newsapi.org/v2/top-headlines?country=pk&apiKey=YOUR_KEY`
- **Everything**: `https://newsapi.org/v2/everything?q=query&apiKey=YOUR_KEY`
- **By Category**: `https://newsapi.org/v2/top-headlines?country=pk&category=sports&apiKey=YOUR_KEY`

### Supported Countries:
- Pakistan: `pk`
- United States: `us`
- United Kingdom: `gb`
- And many more...

### Supported Categories:
- `business`
- `entertainment`
- `general`
- `health`
- `science`
- `sports`
- `technology`

## 2. Weather API Setup (Optional)

### Step 1: Get OpenWeatherMap API Key
1. Visit [OpenWeatherMap.org](https://openweathermap.org/api)
2. Click on "Sign Up" to create a free account
3. Navigate to "API Keys" section
4. Generate a new API key (free tier allows 60 calls/minute)

### Step 2: Configure Weather API Key
1. Open the file: `app/src/main/java/com/example/finalproject/utils/Constants.java`
2. Find the line: `public static final String WEATHER_API_KEY = "YOUR_WEATHER_API_KEY_HERE";`
3. Replace `YOUR_WEATHER_API_KEY_HERE` with your actual API key

### Weather API Endpoints Used:
- **Current Weather**: `https://api.openweathermap.org/data/2.5/weather?q=city&appid=YOUR_KEY`
- **5-Day Forecast**: `https://api.openweathermap.org/data/2.5/forecast?q=city&appid=YOUR_KEY`

## 3. Alternative: Using Demo Mode

If you don't want to set up APIs immediately, the application includes demo data that will be loaded when API calls fail. This allows you to test the UI and functionality without API keys.

## 4. Important Notes

### Internet Permission
The app requires internet permission, which is already added in `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### API Rate Limits
- **NewsAPI Free Tier**: 100 requests per day
- **OpenWeatherMap Free Tier**: 60 calls per minute, 1,000,000 calls per month

### Security Best Practices
⚠️ **IMPORTANT**: Never commit your API keys to version control (Git). Consider:
1. Using environment variables
2. Using a local configuration file that's in `.gitignore`
3. Using Android's BuildConfig for release builds

## 5. Testing Your API Keys

After adding your API keys:
1. Build and run the application
2. Navigate to the Home screen
3. Check if news articles are loading
4. If articles don't load, check Logcat for error messages
5. Verify your API key is correct and has available quota

## 6. Troubleshooting

### News Not Loading
- Verify your API key is correct
- Check your internet connection
- Verify you haven't exceeded rate limits
- Check Logcat for specific error messages

### Weather Not Loading
- Ensure Weather API key is set
- Verify city name is correct
- Check API quota limits

## 7. API Documentation Links

- **NewsAPI**: https://newsapi.org/docs
- **OpenWeatherMap**: https://openweathermap.org/api

---

**Note**: The application will work with demo data if API keys are not configured, but for full functionality, API keys are required.



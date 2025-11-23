# News Application - Final Project

A comprehensive Android news application with weather forecast and attendance tracking features. This app displays latest news from Pakistan and worldwide, provides weather forecasts, and includes an attendance management system.

## Features

### 📰 News Features
- **Home Screen**: 
  - Personalized welcome header
  - News sources selection (BBC, RTE, ITV, Forbes, Fox, etc.)
  - Today's trending articles
  - Breaking news with category filters
  - Bottom navigation

- **Breaking News Screen**:
  - Search functionality
  - Category filters (Trending, Politics, Sports, Health, International)
  - Featured news section
  - Latest news feed

- **Article Detail Screen**:
  - Full article view with images
  - Like, comment, share, and bookmark functionality
  - Author and source information
  - Time-based article metadata

### 🌤️ Weather Features
- Current weather display
- 5-day weather forecast
- Humidity and wind speed information
- Beautiful card-based UI

### 📋 Attendance Features
- Mark student attendance
- View attendance history
- Subject-based tracking
- Date and time stamps

## Project Structure

```
app/src/main/java/com/example/finalproject/
├── activities/
│   ├── HomeActivity.java              # Main home screen
│   ├── BreakingNewsActivity.java     # Breaking news with search
│   ├── ArticleDetailActivity.java    # Full article view
│   ├── WeatherActivity.java          # Weather forecast
│   └── AttendanceActivity.java       # Attendance management
├── adapters/
│   ├── NewsAdapter.java              # RecyclerView adapter for news
│   ├── NewsSourceAdapter.java        # Adapter for news sources
│   ├── CategoryAdapter.java          # Adapter for categories
│   └── AttendanceAdapter.java        # Adapter for attendance list
├── api/
│   ├── ApiClient.java                # Retrofit client setup
│   └── NewsApiService.java           # News API interface
├── models/
│   ├── NewsArticle.java              # News article model
│   ├── NewsResponse.java             # API response model
│   ├── Source.java                   # News source model
│   ├── Weather.java                  # Weather data model
│   └── Attendance.java               # Attendance model
└── utils/
    ├── Constants.java                # App constants and API keys
    └── TimeUtils.java                # Time formatting utilities
```

## Setup Instructions

### 1. Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK (API 24+)
- Internet connection for API calls

### 2. API Keys Setup

#### News API
1. Visit [NewsAPI.org](https://newsapi.org/)
2. Sign up for a free account
3. Get your API key
4. Open `app/src/main/java/com/example/finalproject/utils/Constants.java`
5. Replace `YOUR_NEWS_API_KEY_HERE` with your actual API key

#### Weather API (Optional)
1. Visit [OpenWeatherMap.org](https://openweathermap.org/api)
2. Sign up for a free account
3. Get your API key
4. Update `WEATHER_API_KEY` in `Constants.java`

**Note**: The app works with demo data if API keys are not configured.

### 3. Build and Run
1. Open the project in Android Studio
2. Sync Gradle files
3. Build the project (Build > Make Project)
4. Run on an emulator or physical device

## Dependencies

The project uses the following main libraries:
- **Retrofit 2.9.0**: HTTP client for API calls
- **Gson 2.10.1**: JSON parsing
- **Glide 4.16.0**: Image loading and caching
- **Material Components**: Modern UI components
- **RecyclerView**: Efficient list rendering
- **CardView**: Card-based UI elements

## UI Design

The application follows Material Design principles with:
- Clean and modern interface
- Card-based layouts
- Smooth scrolling
- Intuitive navigation
- Responsive design

## API Information

### News API
- **Base URL**: `https://newsapi.org/v2/`
- **Endpoints Used**:
  - `/top-headlines` - Get top headlines by country
  - `/everything` - Search articles
  - `/top-headlines` with category - Get news by category

### Weather API
- **Base URL**: `https://api.openweathermap.org/data/2.5/`
- **Endpoints Used**:
  - `/weather` - Current weather
  - `/forecast` - 5-day forecast

For detailed API setup instructions, see [API_SETUP.md](API_SETUP.md)

## Features Implementation

### News Feed
- Fetches latest news from Pakistan and worldwide
- Displays articles in horizontal and vertical RecyclerViews
- Supports category filtering
- Real-time search functionality

### Weather Forecast
- Shows current weather conditions
- Displays 5-day forecast
- Includes humidity and wind speed

### Attendance System
- Mark student attendance
- Track by subject
- View attendance history
- Date and time tracking

## Navigation

The app uses bottom navigation with four main sections:
1. **Home**: Main news feed
2. **Explore**: Breaking news and search
3. **Weather**: Weather forecast
4. **Attendance**: Attendance management

## Code Quality

- Clean architecture with separation of concerns
- Reusable components and adapters
- Proper error handling
- Demo data fallback for offline testing
- Material Design guidelines

## Future Enhancements

- [ ] Offline caching with Room database
- [ ] Push notifications for breaking news
- [ ] User authentication
- [ ] Bookmark articles locally
- [ ] Share articles functionality
- [ ] Dark mode support
- [ ] Multi-language support

## Troubleshooting

### News not loading?
- Check your internet connection
- Verify API key is correct
- Check API quota limits
- Review Logcat for error messages

### Build errors?
- Sync Gradle files
- Clean and rebuild project
- Check Android SDK version
- Verify all dependencies are downloaded

## License

This project is created for educational purposes.

## Contact

For issues or questions, please refer to the project documentation or contact the development team.

---

**Note**: Remember to keep your API keys secure and never commit them to version control!



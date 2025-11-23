# Image Placement Guide for Android Project

## 📁 Folder Structure for Images

### 1. **`app/src/main/res/drawable/`** ✅ **BEST FOR MOST IMAGES**
   - **Use for:** Logos, icons, backgrounds, illustrations, button images
   - **Formats:** PNG, JPG, WebP, XML (vector drawables)
   - **Example files:**
     - `logo.jpg` ✅ (Your app logo)
     - `icon_news.png`
     - `background_pattern.png`
     - `button_background.xml`

   **How to use in code:**
   ```xml
   <ImageView
       android:src="@drawable/logo"
       ... />
   ```
   
   ```java
   imageView.setImageResource(R.drawable.logo);
   ```

### 2. **`app/src/main/res/mipmap/`** ⚠️ **ONLY FOR APP ICON**
   - **Use for:** App launcher icon ONLY (the icon on home screen)
   - **Different densities:**
     - `mipmap-mdpi/` - Medium density
     - `mipmap-hdpi/` - High density
     - `mipmap-xhdpi/` - Extra high density
     - `mipmap-xxhdpi/` - Extra extra high density
     - `mipmap-xxxhdpi/` - Extra extra extra high density

### 3. **Density-Specific Folders (Optional)**
   - `drawable-hdpi/` - For high-density screens
   - `drawable-xhdpi/` - For extra high-density screens
   - `drawable-xxhdpi/` - For extra extra high-density screens
   - `drawable-xxxhdpi/` - For extra extra extra high-density screens
   
   **When to use:** Only if you have different versions of the same image for different screen densities

## 📋 Recommended Structure

```
app/src/main/res/
├── drawable/              ← PUT YOUR IMAGES HERE ✅
│   ├── logo.jpg          ← Your app logo
│   ├── icon_news.png
│   ├── icon_weather.png
│   ├── background.xml
│   └── ...
│
├── mipmap-*/             ← App icon only (already set up)
│   └── ic_launcher.webp
│
└── values/
    └── ...
```

## 🎯 For Your Project

### Current Image: `logo.jpg`

**✅ Correct Location:**
```
app/src/main/res/drawable/logo.jpg
```

**How to use it:**
1. **In XML layouts:**
   ```xml
   <ImageView
       android:id="@+id/appLogo"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:src="@drawable/logo"
       android:contentDescription="App Logo" />
   ```

2. **In Java code:**
   ```java
   ImageView logoView = findViewById(R.id.appLogo);
   logoView.setImageResource(R.drawable.logo);
   ```

3. **With Glide (for better performance):**
   ```java
   Glide.with(this)
       .load(R.drawable.logo)
       .into(imageView);
   ```

## 📝 Best Practices

1. **File Naming:**
   - Use lowercase letters
   - Use underscores instead of spaces
   - Examples: `logo.jpg`, `icon_news.png`, `bg_home.jpg`

2. **Image Formats:**
   - **PNG** - Best for icons, logos with transparency
   - **JPG** - Best for photos, complex images
   - **WebP** - Best compression, smaller file size
   - **XML** - Vector drawables (scalable, no quality loss)

3. **File Size:**
   - Keep images optimized
   - Use WebP format when possible (smaller size)
   - Compress images before adding to project

4. **Density:**
   - For most cases, put images in `drawable/` (Android will scale automatically)
   - Only use density-specific folders if you have different versions

## 🔧 Converting logo.jpg to App Icon

If you want to use `logo.jpg` as the app icon:

1. **Convert to different sizes:**
   - 48x48 (mdpi)
   - 72x72 (hdpi)
   - 96x96 (xhdpi)
   - 144x144 (xxhdpi)
   - 192x192 (xxxhdpi)

2. **Place in mipmap folders:**
   ```
   mipmap-mdpi/ic_launcher.png
   mipmap-hdpi/ic_launcher.png
   mipmap-xhdpi/ic_launcher.png
   mipmap-xxhdpi/ic_launcher.png
   mipmap-xxxhdpi/ic_launcher.png
   ```

3. **Or use online tools:**
   - https://icon.kitchen/ (Android Icon Generator)
   - Android Studio: Right-click drawable → New → Image Asset

## ✅ Summary

**For your `logo.jpg`:**
- ✅ **Place in:** `app/src/main/res/drawable/logo.jpg`
- ✅ **Reference as:** `@drawable/logo` or `R.drawable.logo`
- ✅ **Already moved:** The file has been copied to the correct location!

**For other images:**
- ✅ **General images:** `res/drawable/`
- ⚠️ **App icon only:** `res/mipmap-*/`



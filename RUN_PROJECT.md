# How to Run Android Project from Terminal (VS Code)

## Prerequisites

Before running the project, make sure you have:

1. **Android SDK** installed (usually at `/home/imtiaz-ali/Android/Sdk`)
2. **Java JDK 11 or higher** installed
3. **Android device/emulator** connected or running

## Step-by-Step Instructions

### Step 1: Open Terminal in VS Code

1. Open VS Code
2. Open the project folder: `FinalProject`
3. Open terminal: Press `Ctrl + ~` (or `Ctrl + `` ` ``) or go to `Terminal > New Terminal`

### Step 2: Navigate to Project Directory

```bash
cd "/home/imtiaz-ali/Downloads/Sindh Agriculuture university data/6th semeseter data/FinalProject"
```

Or if you're already in VS Code with the project open, you should already be in the right directory.

### Step 3: Check Prerequisites

#### Check Java Version
```bash
java -version
```
Should show Java 11 or higher.

#### Check Android SDK Path
```bash
cat local.properties
```
Should show: `sdk.dir=/home/imtiaz-ali/Android/Sdk`

#### Check if Android SDK is in PATH
```bash
echo $ANDROID_HOME
```
If empty, you may need to set it (see below).

### Step 4: Set Environment Variables (if needed)

If Android SDK is not in your PATH, add these to your `~/.bashrc` or `~/.zshrc`:

```bash
export ANDROID_HOME=/home/imtiaz-ali/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

Then reload:
```bash
source ~/.bashrc
# or
source ~/.zshrc
```

### Step 5: Make Gradle Wrapper Executable

```bash
chmod +x gradlew
```

### Step 6: Check Connected Devices/Emulators

```bash
$ANDROID_HOME/platform-tools/adb devices
```

You should see:
- An emulator (if running)
- A physical device (if connected via USB with USB debugging enabled)

**If no device is connected:**
- Start an Android emulator from Android Studio, OR
- Connect a physical Android device with USB debugging enabled

### Step 7: Build the Project

#### Clean previous builds (optional but recommended):
```bash
./gradlew clean
```

#### Build the APK:
```bash
./gradlew assembleDebug
```

This will create an APK file at:
```
app/build/outputs/apk/debug/app-debug.apk
```

#### Build and Install directly:
```bash
./gradlew installDebug
```

This builds and installs the app on your connected device/emulator.

### Step 8: Run the App

#### Option A: Install and Launch
```bash
./gradlew installDebug && $ANDROID_HOME/platform-tools/adb shell am start -n com.example.finalproject/.MainActivity
```

#### Option B: Just Install (then launch manually from device)
```bash
./gradlew installDebug
```
Then open the app from your device/emulator.

### Step 9: View Logs (Optional)

To see app logs in real-time:
```bash
$ANDROID_HOME/platform-tools/adb logcat | grep -i "finalproject"
```

Or see all logs:
```bash
$ANDROID_HOME/platform-tools/adb logcat
```

## Quick Commands Summary

```bash
# Navigate to project
cd "/home/imtiaz-ali/Downloads/Sindh Agriculuture university data/6th semeseter data/FinalProject"

# Make gradlew executable (first time only)
chmod +x gradlew

# Clean build
./gradlew clean

# Build APK
./gradlew assembleDebug

# Build and install
./gradlew installDebug

# Check connected devices
$ANDROID_HOME/platform-tools/adb devices

# View logs
$ANDROID_HOME/platform-tools/adb logcat
```

## Troubleshooting

### Error: "gradlew: command not found"
**Solution:** Make sure you're in the project root directory and run:
```bash
chmod +x gradlew
./gradlew --version
```

### Error: "SDK location not found"
**Solution:** Check `local.properties` file exists and has correct SDK path:
```bash
cat local.properties
```
Should show: `sdk.dir=/home/imtiaz-ali/Android/Sdk`

### Error: "No connected devices"
**Solution:** 
1. Start an Android emulator from Android Studio
2. Or connect a physical device with USB debugging enabled
3. Check with: `adb devices`

### Error: "Java version not compatible"
**Solution:** Install Java 11 or higher:
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

### Build Fails with Dependency Errors
**Solution:** 
```bash
./gradlew clean
./gradlew --refresh-dependencies
./gradlew build
```

### Permission Denied for gradlew
**Solution:**
```bash
chmod +x gradlew
```

## Alternative: Using Android Studio (Recommended)

While you can use VS Code and terminal, **Android Studio** is the recommended IDE for Android development because it:
- Has built-in emulator
- Better debugging tools
- Automatic Gradle sync
- Better error messages
- Integrated device manager

### To use Android Studio:
1. Download Android Studio from https://developer.android.com/studio
2. Open the project: `File > Open > Select FinalProject folder`
3. Wait for Gradle sync
4. Click the green "Run" button or press `Shift + F10`

## VS Code Extensions (Optional)

If you want better Android support in VS Code, install:
- **Android iOS Emulator** extension
- **Java Extension Pack**
- **Gradle for Java**

## Notes

- The first build will take longer as Gradle downloads dependencies
- Make sure you have internet connection for first build
- APK file will be in `app/build/outputs/apk/debug/app-debug.apk`
- You can manually install APK: `adb install app/build/outputs/apk/debug/app-debug.apk`



#!/bin/bash

# Android Project Build and Run Script
# Usage: ./run.sh

echo "=========================================="
echo "Android Project Build & Run Script"
echo "=========================================="
echo ""

# Check if we're in the project directory
if [ ! -f "gradlew" ]; then
    echo "❌ Error: gradlew not found. Make sure you're in the project root directory."
    exit 1
fi

# Make gradlew executable
echo "📝 Making gradlew executable..."
chmod +x gradlew

# Check Android SDK
if [ -z "$ANDROID_HOME" ]; then
    if [ -f "local.properties" ]; then
        SDK_DIR=$(grep "sdk.dir" local.properties | cut -d'=' -f2)
        export ANDROID_HOME=$SDK_DIR
        export PATH=$PATH:$ANDROID_HOME/platform-tools
        echo "✅ Using SDK from local.properties: $ANDROID_HOME"
    else
        echo "❌ Error: ANDROID_HOME not set and local.properties not found"
        exit 1
    fi
else
    echo "✅ ANDROID_HOME is set: $ANDROID_HOME"
fi

# Check connected devices
echo ""
echo "📱 Checking connected devices..."
DEVICES=$($ANDROID_HOME/platform-tools/adb devices | grep -v "List" | grep "device" | wc -l)

if [ "$DEVICES" -eq 0 ]; then
    echo "⚠️  Warning: No devices/emulators connected!"
    echo "   Please start an emulator or connect a device"
    echo ""
    read -p "Continue anyway? (y/n) " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
else
    echo "✅ Found $DEVICES device(s) connected"
    $ANDROID_HOME/platform-tools/adb devices
fi

# Clean previous build
echo ""
echo "🧹 Cleaning previous build..."
./gradlew clean

# Build the project
echo ""
echo "🔨 Building project..."
./gradlew assembleDebug

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful!"

# Install on device
echo ""
echo "📲 Installing app on device..."
./gradlew installDebug

if [ $? -ne 0 ]; then
    echo "❌ Installation failed!"
    exit 1
fi

echo "✅ Installation successful!"

# Launch the app
echo ""
echo "🚀 Launching app..."
$ANDROID_HOME/platform-tools/adb shell am start -n com.example.finalproject/.MainActivity

if [ $? -eq 0 ]; then
    echo "✅ App launched successfully!"
    echo ""
    echo "=========================================="
    echo "✅ All done! App should be running now."
    echo "=========================================="
else
    echo "⚠️  Could not launch app automatically"
    echo "   Please open the app manually from your device"
fi

echo ""
echo "📋 To view logs, run:"
echo "   $ANDROID_HOME/platform-tools/adb logcat | grep -i finalproject"



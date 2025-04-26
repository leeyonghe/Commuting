# AOSKotlin Android App

This is the Android client for the work time tracking application.

## Project Structure

```
android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── aos_kotlin/
│   │       │           ├── database/
│   │       │           ├── fragment/
│   │       │           ├── notification/
│   │       │           ├── preference/
│   │       │           └── MainActivity.kt
│   │       └── res/
│   │           ├── layout/
│   │           ├── values/
│   │           └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Prerequisites

- Android Studio Arctic Fox or later
- JDK 17
- Android SDK 33 (Android 13)
- Gradle 7.5

## Building the Project

1. Open the project in Android Studio
2. Wait for the Gradle sync to complete
3. Build the project using the Build menu or the Gradle tasks

## Running the App

1. Connect an Android device or start an emulator
2. Click the Run button in Android Studio
3. Select your target device
4. Click OK to install and run the app

## Features

- Record work start and end times
- Calculate work duration
- View weekly and monthly statistics
- Set work hour preferences
- Receive overtime notifications
- Data persistence using Room database

## Dependencies

- AndroidX Core
- AndroidX AppCompat
- Material Design
- Room Database
- WorkManager
- ViewBinding
- Kotlin Coroutines 
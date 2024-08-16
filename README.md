# CloudVideoApp

CloudVideoApp is a simple Android project that demonstrates how to capture, upload, and view videos using [Cloudinary](https://cloudinary.com/). The project utilizes the Cloudinary Android SDK and provides features like video recording, uploading with progress tracking, camera switching, and viewing the uploaded video.

## Features

- Capture video using the camera
- Upload video to Cloudinary
- Display upload progress
- Switch between front and back cameras
- View the video after a successful upload

## Prerequisites

- Android Studio 
- Minimum Android SDK 24
- A [Cloudinary](https://cloudinary.com/) account

## Getting Started

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/nngarfdn/CloudVideoApp.git
cd CloudVideoApp
```

### 2. Configure Cloudinary Credentials
Create keystore.properties:

In the root of your project (where the build.gradle.kts file is located), create a file named keystore.properties:
```keystore.properties
CLOUDINARY_API_KEY=your_cloudinary_api_key
CLOUDINARY_API_SECRET=your_cloudinary_api_secret
```

### 3. Update App.kt with Your Cloud Name
You need to update the App.kt file to include your Cloudinary cloud name and use the credentials stored in BuildConfig.
```App.kt
CLOUD_NAME = your_cloud_name
```

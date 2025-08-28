plugins {
    id("com.android.application")
}

android {
    namespace = "com.unidad.gymapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.unidad.gymapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.airbnb.android:lottie:6.5.0")
}

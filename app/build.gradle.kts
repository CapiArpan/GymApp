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
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.airbnb.android:lottie:6.5.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
}

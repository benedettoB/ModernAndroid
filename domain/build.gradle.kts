plugins {
    alias(libs.plugins.library.plug)
    alias(libs.plugins.kotlin.plug)
}

android {
    namespace = "com.benedetto.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //threading
    implementation(libs.coroutines)
    //di
    implementation(libs.daggerHilt)
    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}
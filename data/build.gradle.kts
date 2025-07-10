plugins {
    alias(libs.plugins.library.plug)
    alias(libs.plugins.kotlin.plug)
}

android {
    namespace = "com.benedetto.data"
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
    //library modules
    implementation(project(":domain"))
    //serialization
    implementation(libs.retrofit.gson)
    //di
    implementation(libs.daggerHilt)
    //networking
    implementation(libs.network.retrofit)
    //threading
    implementation(libs.coroutines)
    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}
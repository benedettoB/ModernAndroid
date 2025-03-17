plugins {
    alias(libs.plugins.library.plug)
    alias(libs.plugins.kotlin.plug)
}

android {
    namespace = "com.benedetto.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //threading
    implementation(libs.coroutines)
    //di
    implementation(libs.dagger.hilt.android)
    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}
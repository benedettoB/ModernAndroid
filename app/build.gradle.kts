plugins {
    alias(libs.plugins.application.plug)
    alias(libs.plugins.kotlin.plug)
    alias(libs.plugins.compose.plug)
    alias(libs.plugins.hilt.plug)
    alias(libs.plugins.ksp.plug)
}

android {
    namespace = "com.benedetto.modernandroid"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        targetSdk = 35
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //library modules
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":galoislibrary"))
    //di
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.ui.test.junit4.android)
    ksp(libs.hilt.compiler)
    implementation(libs.compose.hilt.navigation)
    //androidx core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.accompanist.permissions)
    //compose
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.lifecycle)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.graphics)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)
    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
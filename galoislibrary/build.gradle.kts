plugins {
    alias(libs.plugins.library.plug)
    alias(libs.plugins.kotlin.plug)
}
android {
    namespace = "com.benedetto.galoislibrary"
    compileSdk = 35
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild{
            cmake {
                cppFlags("")
                abiFilters("armeabi-v7a", "arm64-v8a", "x86", "x86_64") // Architectures to build for
            }
        }
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    externalNativeBuild{
        cmake{
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
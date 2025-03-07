plugins {
    alias(libs.plugins.library.plug)
    alias(libs.plugins.kotlin.plug)
    alias(libs.plugins.apollo.plug)
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
    implementation(libs.auth)
    //networking
    implementation(libs.retrofit)
    api(libs.okhttpInterceptor)
    implementation(libs.apollo)
    //threading
    implementation(libs.coroutines)

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}
apollo {
    service("service") {
        packageName.set("com.benedetto.data")
        introspection {
            endpointUrl.set("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}
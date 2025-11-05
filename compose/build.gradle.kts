plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.oliviermarteaux.shared.compose"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

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
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
    buildFeatures {
        compose = true
    }
    // Add JVM tooolchain to define global java version
    kotlin { jvmToolchain(17) }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":core"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.google.accompanist.systemuicontroller)
    implementation(libs.material.icons.extended)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
}
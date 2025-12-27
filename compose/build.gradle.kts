plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
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

    buildFeatures {
        compose = true
    }

    // Add JVM tooolchain to define global java version
    kotlin { jvmToolchain(17) }

    publishing {
        singleVariant("release") {
            withSourcesJar()   // <-- generates sources.jar
            withJavadocJar()   // optional but recommended
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])  // <-- works because publishing.singleVariant() was configured
            }
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":core"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.google.accompanist.systemuicontroller)
    implementation(libs.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx) // Navigation
    //_ hilt for DI
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    // _ Google Accompanist for permissions
    implementation(libs.google.accompanist)
    // _ GoogleMaps
    // Google Maps Compose
    implementation(libs.googlemaps.android)
    // Google Maps SDK
    implementation(libs.googlemaps.sdk)
    //_ cameraX for camera capture
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)

    testImplementation(libs.junit)
}
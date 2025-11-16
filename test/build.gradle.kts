plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.oliviermarteaux.shared.test"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.activity.compose)
    implementation(libs.junit)
    implementation(libs.ui.test.junit4)
    implementation(libs.kotlinx.coroutines.test)
}
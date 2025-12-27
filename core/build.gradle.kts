plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
    // Add the dependency for the Google services Gradle plugin for Firebase authentication
    alias(libs.plugins.googleservices)
}

android {
    namespace = "com.oliviermarteaux.shared.core"
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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    //_ Firebase
    implementation(platform(libs.firebase.bom)) // Bom
    implementation(libs.firebase.analytics)  // Google Analytics
    implementation(libs.firebase.auth) // Authentication
    implementation(libs.firebase.firestore) // Database
    implementation(libs.firebase.messaging) // Cloud notifications
    implementation(libs.firebase.storage) // Media files storage
    // For google account authentication
    implementation(libs.play.services.credentials)
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)
    //_ Preferences DataStore
    implementation(libs.datastore.preferences)

    testImplementation(libs.junit)
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    // for Hilt DI
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    // Add the dependency for the Google services Gradle plugin for Firebase authentication
    alias(libs.plugins.googleservices) apply false
}

allprojects {
    group = "com.github.oliviermarteaux"
}
package com.oliviermarteaux.shared.firebase.authentication.data.service

import android.content.Intent

sealed class GoogleSignInResult {
    data class Success(val idToken: String) : GoogleSignInResult()
    data class LaunchIntent(val intent: Intent) : GoogleSignInResult()
    data class Error(val exception: Throwable) : GoogleSignInResult()
}
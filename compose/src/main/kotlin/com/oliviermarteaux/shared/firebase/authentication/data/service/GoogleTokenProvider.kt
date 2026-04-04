package com.oliviermarteaux.shared.firebase.authentication.data.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleTokenProvider @Inject constructor(
    private val credentialManager: CredentialManager,
) {

    suspend fun signIn(serverClientId: String, activity: Activity): GoogleSignInResult {
        return if (isModernDevice()) {
            signInWithCredentialManager(serverClientId, activity)
        } else {
            signInLegacy(serverClientId, activity)
        }
    }

    private fun isModernDevice(): Boolean {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        return true
    }

    // -----------------------------
    // MODERN FLOW (Credential Manager)
    // -----------------------------
    private suspend fun signInWithCredentialManager(
        serverClientId: String, activity: Activity
    ): GoogleSignInResult {
        return try {
            // This variable will hold the credential from either the silent or interactive attempt.
            val credential = try {
                // --- ATTEMPT 1: SILENT SIGN-IN ---
                Log.d("OM_TAG", "UserFirebaseApi::signInWithGoogle: Attempting silent sign-in...")
                val silentSignInOption = GetGoogleIdOption.Builder()
                    .setServerClientId(serverClientId)
                    .setFilterByAuthorizedAccounts(true) // <<< Key for silent sign-in
                    .setAutoSelectEnabled(true)
                    .build()

                val silentRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(silentSignInOption)
                    .build()

                credentialManager.getCredential(activity, silentRequest).credential

            } catch (e: NoCredentialException) {
                // --- FALLBACK: INTERACTIVE SIGN-IN ---
                // This is an expected failure for new users. Fall back to the interactive flow.
                Log.d("OM_TAG", "GoogleTokenProvider::signInWithCredentialManager: [expected] Silent sign-in failed, falling back to interactive...")
                val interactiveSignInOption = GetGoogleIdOption.Builder()
                    .setServerClientId(serverClientId)
                    .setFilterByAuthorizedAccounts(false) // <<< Key for interactive sign-in
                    .build()

                val interactiveRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(interactiveSignInOption)
                    .build()

                credentialManager.getCredential(activity, interactiveRequest).credential
            }

            if (credential is CustomCredential &&
                credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val idToken = GoogleIdTokenCredential
                    .createFrom(credential.data)
                    .idToken

                GoogleSignInResult.Success(idToken)
            } else {
                val e = IllegalStateException("Invalid credential type")
                Log.e("OM_TAG", "GoogleTokenProvider::signInWithCredentialManager: Interactive SignIn: Credential is not of type Google ID!",e)
                FirebaseCrashlytics.getInstance().recordException(e)
                GoogleSignInResult.Error(e)
            }

        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.e("OM_TAG", "GoogleTokenProvider::signInWithCredentialManager: [expected] Interactive SignIn failure, falling back to legacy SignIn...", e)
//            GoogleSignInResult.Error(e)
            try {
                signInLegacy(serverClientId, activity)
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                Log.e("OM_TAG", "GoogleTokenProvider::signInWithCredentialManager: Legacy SignIn failure", e)
                GoogleSignInResult.Error(e)
            }
        }
    }

    // -----------------------------
    // LEGACY FLOW (GoogleSignInClient)
    // -----------------------------
    private fun signInLegacy(serverClientId: String, activity: Activity): GoogleSignInResult {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(activity, gso)

        return GoogleSignInResult.LaunchIntent(client.signInIntent)
    }

    // Called AFTER Activity result
    fun handleLegacyResult(data: Intent?): GoogleSignInResult {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            GoogleSignInResult.Success(account.idToken!!)
        } catch (e: Exception) {
            GoogleSignInResult.Error(e)
        }
    }
}
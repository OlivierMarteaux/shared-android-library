package com.oliviermarteaux.shared.firebase.authentication.ui.screen.splash

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.data.service.GoogleSignInResult
import com.oliviermarteaux.shared.firebase.authentication.data.service.GoogleTokenProvider
import com.oliviermarteaux.shared.firebase.authentication.ui.AuthUserViewModel
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val log: Logger,
    isOnlineFlow: Flow<Boolean>,
    val provider: GoogleTokenProvider
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    var authErrorMessage: String by mutableStateOf("")
        private set
    private val _launchIntent = MutableSharedFlow<Intent>()
    private var onSuccessfulGoogleSignInDestination: () -> Unit = {}

    fun setDestination(onSuccess: () -> Unit) {
        onSuccessfulGoogleSignInDestination = onSuccess
    }

    fun resolveGoogleSignInProvider(serverClientIdString: String, activity: Activity) = viewModelScope.launch {

        when (val result = provider.signIn(serverClientIdString, activity )) {

            is GoogleSignInResult.Success -> {
                log.d("SplashViewModel::resolveGoogleSignInProvider: successfully logged")
                log.d("SplashViewModel::signInWithGoogle: destination: $onSuccessfulGoogleSignInDestination")
                userRepository.signInWithGoogle(result.idToken)
                onSuccessfulGoogleSignInDestination()
            }

            is GoogleSignInResult.LaunchIntent -> {
                log.d("SplashViewModel::resolveGoogleSignInProvider: launched intent")
                _launchIntent.emit(result.intent) // UI handles it
            }

            is GoogleSignInResult.Error -> {
                val e = result.exception
                FirebaseCrashlytics.getInstance().recordException(e)
                log.e("splashViewModel::signInWithGoogle: failed to log",e)
                authErrorMessage = (e.message ?: "Google authentication error")+", try with email"
                showAuthErrorToast()
            }
        }
    }

    suspend fun signInWithGoogle(idToken: String) {
        log.d("SplashViewModel::signInWithGoogle: successfully logged")
        log.d("SplashViewModel::signInWithGoogle: destination: $onSuccessfulGoogleSignInDestination")
        userRepository.signInWithGoogle(idToken)
        onSuccessfulGoogleSignInDestination()
    }
}
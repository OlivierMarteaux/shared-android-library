package com.oliviermarteaux.shared.firebase.authentication.ui.screen.splash

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.ui.AuthUserViewModel
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getString
import coil3.util.CoilUtils.result
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.oliviermarteaux.shared.firebase.authentication.data.service.GoogleSignInResult
import com.oliviermarteaux.shared.firebase.authentication.data.service.GoogleTokenProvider
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserFirebaseApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>,
    val provider: GoogleTokenProvider
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    var authErrorMessage: String by mutableStateOf("")
        private set

    private val _launchIntent = MutableSharedFlow<Intent>()
    val launchIntent = _launchIntent.asSharedFlow() // UI observes this

//    fun signInWithGoogle(@StringRes serverClientIdStringRes: Int, onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            userRepository.signInWithGoogle(serverClientIdStringRes).fold(
//                onSuccess = {
//                    log.d("splashViewModel::signInWithGoogle: successfully logged")
//                    onSuccess()
//                            },
//                onFailure = { e ->
//                    log.e("splashViewModel::signInWithGoogle: failed to log",e)
//                    authErrorMessage = e.message ?: "Google authentication error, try with email"
//                    showAuthErrorToast()
//                }
//            )
//        }
//    }
    private var onSuccessfulGoogleSignInDestination: () -> Unit = {}

    fun setDestination(onSuccess: () -> Unit) {
        onSuccessfulGoogleSignInDestination = onSuccess
    }

    fun resolveGoogleSignInProvider(serverClientIdString: String) = viewModelScope.launch {

        when (val result = provider.signIn(serverClientIdString)) {

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
package com.oliviermarteaux.shared.firebase.authentication.ui.screen.splash

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

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>,
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    var authErrorMessage: String by mutableStateOf("")
        private set

    fun signInWithGoogle(@StringRes serverClientIdStringRes: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.signInWithGoogle(serverClientIdStringRes).fold(
                onSuccess = {
                    log.d("splashViewModel::signInWithGoogle: successfully logged")
                    onSuccess()
                            },
                onFailure = { e ->
                    log.e("splashViewModel::signInWithGoogle: failed to log",e)
                    authErrorMessage = e.message ?: "Google authentication error, try with email"
                    showAuthErrorToast()
                }
            )
        }
    }
}
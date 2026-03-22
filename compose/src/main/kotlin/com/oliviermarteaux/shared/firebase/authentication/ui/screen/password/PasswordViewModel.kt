package com.oliviermarteaux.shared.firebase.authentication.ui.screen.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.ui.AuthUserViewModel
import com.oliviermarteaux.shared.ui.showToastFlag
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for the Password screen.
 *
 * @param savedStateHandle The saved state handle for the view model.
 * @param userRepository The repository for managing user data.
 * @param log The logger.
 * @param isOnlineFlow A flow that emits the current internet connection status.
 */
@HiltViewModel
class PasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>,
    private val dispatchers: CoroutineDispatcherProvider,
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    /**
     * The email address of the user.
     */
    val email: String = checkNotNull(savedStateHandle["email"])
    /**
     * A boolean indicating if the password is incorrect.
     */
    var incorrectPassword: Boolean by mutableStateOf(false)
        private set
    /**
     * The password entered by the user.
     */
    var password: String by mutableStateOf("")
        private set

    var emailVerification: Boolean by mutableStateOf(false)
        private set
    var emailNotVerifiedError: Boolean by mutableStateOf(false)
        private set
    /**
     * Updates the password.
     *
     * @param newPassword The new password.
     */
    fun onPasswordChange(newPassword: String) { password = newPassword }
    /**
     * Shows a toast message for an incorrect password.
     */
    fun showIncorrectPasswordToast() = viewModelScope.launch { showToastFlag{ incorrectPassword = it } }
    /**
     * Signs in the user.
     *
     * @param password The password to sign in with.
     * @param onSignIn A callback to invoke when the user is signed in successfully.
     */
    fun signIn(password: String, onSignIn: () -> Unit) = viewModelScope.launch {
        userRepository.signIn(email, password).fold(
            onSuccess = {  onSignIn() },
            onFailure = { error ->
                when (error) {
                    is IllegalStateException -> {
                        log.e("PasswordViewModel: signIn: ${error.message ?: ""}")
                        showEmailVerificationAlertDialog()
                    }
                    is FirebaseAuthInvalidCredentialsException,
                    is IllegalArgumentException -> {
                        log.e("PasswordViewModel: signIn: Invalid credentials: ${ error.message ?: "" }")
                        showIncorrectPasswordToast()
                    }
                    is FirebaseNetworkException -> showNetworkErrorToast()
                    else -> {
                        log.e("PasswordViewModel: Unknown error: ${error.message}", error)
                        showUnknownErrorToast()
                    }
                }
            }
        )
    }

    fun showEmailVerificationAlertDialog()= viewModelScope.launch {
        emailVerification = true
    }
    fun hideEmailVerificationAlertDialog()= viewModelScope.launch {
        emailVerification = false
    }
    fun showEmailNotVerifiedErrorToast()= viewModelScope.launch {
        showToastFlag { emailNotVerifiedError = it }
    }
    fun verifyEmail(onEmailVerified: () -> Unit = {}) {
        viewModelScope.launch(dispatchers.io) {
            userRepository.verifyEmail().fold(
                onSuccess = { withContext(dispatchers.main) { onEmailVerified()  }},
                onFailure = { withContext(dispatchers.main) { showEmailNotVerifiedErrorToast() }}
            )
        }
    }
}
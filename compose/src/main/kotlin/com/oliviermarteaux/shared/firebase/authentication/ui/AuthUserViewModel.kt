package com.oliviermarteaux.shared.firebase.authentication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.ui.showToastFlag
import com.oliviermarteaux.shared.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * An abstract view model that provides authentication and user state management.
 *
 * @param userRepository The repository for managing user data.
 * @param log The logger.
 * @param isOnlineFlow A flow that emits the current internet connection status.
 */
abstract class AuthUserViewModel(
    private val userRepository: UserRepository,
    private val log: Logger,
    /*_ isOnlineFlow injected instead of calling checkInternetConnection() to make class easier
        to test. */
    private val isOnlineFlow: Flow<Boolean>,
) : ViewModel() {

    var userAuthState: UserAuthState by mutableStateOf(UserAuthState.Loading)
        private set
    /**
     * A boolean indicating if the device is online.
     */
    var isOnline: Boolean by mutableStateOf(true)
        private set
    /**
     * A boolean indicating if there is an authentication error.
     */
    var authError: Boolean by mutableStateOf(false)
        private set
    /**
     * A boolean indicating if there is a network error.
     */
    var networkError: Boolean by mutableStateOf(false)
        private set
    /**
     * A boolean indicating if there is an unknown error.
     */
    var unknownError: Boolean by mutableStateOf(false)
        private set

    /**
     * Checks the user's authentication state and invokes the appropriate callback.
     *
     * @param onUserLogged The callback to invoke if the user is logged in.
     * @param onNoUserLogged The callback to invoke if the user is not logged in.
     */
    fun checkUserState(
        onUserLogged: (User) -> Unit,
        onNoUserLogged: () -> Unit
    ) {
        when (val state = userAuthState) {
            is UserAuthState.Connected -> {
                log.v("AuthUserViewModel: onAuthUserClick: currentUser = ${state.user.email}")
                onUserLogged(state.user)
            }
            is UserAuthState.NotConnected -> {
                log.v("AuthUserViewModel: onAuthUserClick: no user logged")
                onNoUserLogged()
            }
            is UserAuthState.Loading -> {
                log.v("AuthUserViewModel: onAuthUserClick: no user logged")
                onNoUserLogged()
            }
        }
    }
    /**
     * Shows a toast message for a network error.
     */
    fun showNetworkErrorToast() = viewModelScope.launch { showToastFlag { networkError = it } }
    /**
     * Shows a toast message for an unknown error.
     */
    fun showUnknownErrorToast() = viewModelScope.launch { showToastFlag { unknownError = it } }
    /**
     * Shows a toast message for an authentication error.
     */
    fun showAuthErrorToast() = viewModelScope.launch { showToastFlag { authError = it } }

    //_ test functions
    fun disconnectForTest(){
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    //_ auth observers
    private var authObserverDelay: Long = 0
    protected fun setAuthObserverDelay(delay: Long){
        authObserverDelay = delay
    }
    /**
     * Observes the online state of the device.
     */
    private fun observeOnlineState(){
        viewModelScope.launch {
//            delay(200)
//            delay(authObserverDelay)
            isOnlineFlow.collect{
                isOnline = it
                log.v("AuthUserViewModel: checkOnlineState(): online state is $it")
                if (!isOnline) showNetworkErrorToast()
            }
        }
    }
    /**
     * Observes the user's authentication state.
     */
    private fun observeUserAuthState() {
        viewModelScope.launch {
//            delay(200)
//            delay(authObserverDelay)
            userRepository.userAuthState.collect { user ->
                userAuthState = when (user) {
                    null -> UserAuthState.NotConnected
                    else -> UserAuthState.Connected(user)
                }
                log.v("AuthUserViewModel: userAuthState = $userAuthState")
            }
        }
    }

    private var hasResolvedAuth = false

    private fun handleAuthErrors() {
        viewModelScope.launch {
            snapshotFlow { userAuthState }
                .collect { state ->
                    when (state) {
                        is UserAuthState.Loading -> Unit
                        is UserAuthState.Connected -> hasResolvedAuth = true
                        is UserAuthState.NotConnected -> {
                            if (hasResolvedAuth) {
                                showAuthErrorToast()
                            }
                            hasResolvedAuth = true
                        }
                    }
                }
        }
    }

    init {
        observeUserAuthState()
        observeOnlineState()
        handleAuthErrors()
    }
}
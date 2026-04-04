package com.oliviermarteaux.shared.firebase.authentication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.ui.UiState
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

    //_#########################################
    //_## NETWORK ERROR TOAST
    //_#########################################

    var networkError: Boolean by mutableStateOf(false)
        private set
    fun showNetworkErrorToast() = viewModelScope.launch { showToastFlag { networkError = it } }

    //_#########################################
    //_## AUTHENTICATION ERROR TOAST
    //_#########################################

    var authError: Boolean by mutableStateOf(false)
        private set
    fun showAuthErrorToast() = viewModelScope.launch { showToastFlag { authError = it } }

    //_#########################################
    //_## UNKNOWN ERROR TOAST
    //_#########################################

    var unknownError: Boolean by mutableStateOf(false)
        private set

    fun showUnknownErrorToast() = viewModelScope.launch { showToastFlag { unknownError = it } }

    //_#########################################
    //_## SUCCESSFUL ITEM CREATION TOAST
    //_#########################################

    var successfulItemCreation: Boolean by mutableStateOf(false)
        private set
    fun showSuccessfulItemCreationToast() = viewModelScope.launch { showToastFlag { successfulItemCreation = it } }

    //_#########################################
    //_## SUCCESSFUL ITEM UPDATE TOAST
    //_#########################################

    var successfulItemUpdate: Boolean by mutableStateOf(false)
        private set
    fun showSuccessfulItemUpdateToast() = viewModelScope.launch { showToastFlag { successfulItemUpdate = it } }

    //_#########################################
    //_## SUCCESSFUL ITEM DELETION TOAST
    //_#########################################

    var successfulItemDeletion: Boolean by mutableStateOf(false)
        private set
    fun showSuccessfulItemDeletionToast() = viewModelScope.launch { showToastFlag { successfulItemDeletion = it } }

    //_#########################################
    //_## USERS DB OPERATIONS
    //_#########################################

    var currentUser: User? by mutableStateOf(null)
        private set

    var userList: List<User> by mutableStateOf(emptyList())
        private set

    var currentUserUiState: UiState<User?> by mutableStateOf(UiState.Loading)
        private set

    fun getUser() {
        currentUserUiState = UiState.Loading
        checkUserState(
            onUserLogged = { loggedUser ->
                viewModelScope.launch {
                    userRepository.getUserById(loggedUser.id).collect { result ->
                        result.fold(
                            onSuccess = {
                                currentUser = it
                                currentUserUiState = UiState.Success(it)
                                log.v("AuthUserViewModel: getUser(): currentUser = $currentUser")
                                        } ,
                            onFailure = {
                                currentUserUiState = UiState.Error(it)
                                log.e("AuthUserViewModel: getUser(): error = $it")
                                showUnknownErrorToast()
                            }
                        )
                    }
                }
            },
            onNoUserLogged = {
                viewModelScope.launch {
                    currentUser = null
                    log.v("AuthUserViewModel: getUser(): no user logged")
                    showAuthErrorToast()
                }
            }
        )
    }

    private fun getUserById(id: String){
        currentUserUiState = UiState.Loading
        viewModelScope.launch {
            userRepository.getUserById(id).collect { result ->
                result.fold(
                    onSuccess = {
                        currentUser = it
                        currentUserUiState = UiState.Success(it)
                        log.v("AuthUserViewModel: getUserById(): currentUser = $currentUser")
                    },
                    onFailure = {
                        currentUserUiState = UiState.Error(it)
                        log.e("AuthUserViewModel: getUserById(): error = $it")
                        showUnknownErrorToast()
                    }
                )
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect { result ->
                result.fold(
                    onSuccess = {
                        userList = it
                        log.v("AuthUserViewModel: getAllUsers(): userList = $userList")
                                } ,
                    onFailure = {
                        log.e("AuthUserViewModel: getAllUsers(): error = $it")
                        showUnknownErrorToast()
                    }
                )
            }
        }
    }

    fun updateUser(user:User) {
        checkUserState(
            onUserLogged = {
                viewModelScope.launch {
                    currentUser = user
                    userRepository.updateUser(user)
                }
            },
            onNoUserLogged = {
                viewModelScope.launch {
                    log.v("AuthUserViewModel: updateUser(): no user logged")
                    showAuthErrorToast()
                }
            }
        )
    }

    //_#########################################
    //_## AUTHENTICATION STATE MANAGEMENT
    //_#########################################

    var userAuthState: UserAuthState by mutableStateOf(UserAuthState.Loading)
        private set

    fun checkUserState(
        onUserLogged: (User) -> Unit,
        onNoUserLogged: () -> Unit
    ) {
        when (val state = userAuthState) {
            is UserAuthState.Connected -> {
                log.v("AuthUserViewModel: onAuthUserClick: userAuthState email = ${state.user.email}")
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

    private fun observeUserAuthState() {
        viewModelScope.launch {
            userRepository.userAuthState.collect { user ->
                userAuthState = when (user) {
                    null -> UserAuthState.NotConnected
                    else -> {
                        getUserById(user.id)
                        UserAuthState.Connected(user)
                    }
                }
                log.v("AuthUserViewModel::observeUserAuthState: userAuthState = $userAuthState")
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

    //_#########################################
    //_## ONLINE STATE MANAGEMENT
    //_#########################################

    var isOnline: Boolean by mutableStateOf(true)
        private set

    private fun observeOnlineState(){
        viewModelScope.launch {
            isOnlineFlow.collect{
                isOnline = it
                log.v("AuthUserViewModel: checkOnlineState(): online state is $it")
                if (!isOnline) showNetworkErrorToast()
            }
        }
    }

    // _#########################################
    // _## TEST FUNCTIONS
    // _#########################################

    fun disconnectForTest(){
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    // _#########################################
    // _## OBSERVERS DELAYS
    // _#########################################
    private var authObserverDelay: Long = 0
    protected fun setAuthObserverDelay(delay: Long){
        authObserverDelay = delay
    }

    // _#########################################
    // _##  VIEWMODEL INIT
    // _#########################################

    init {
        observeUserAuthState()
        observeOnlineState()
        handleAuthErrors()
    }
}
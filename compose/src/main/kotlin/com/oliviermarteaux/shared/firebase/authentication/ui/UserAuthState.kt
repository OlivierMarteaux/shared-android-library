package com.oliviermarteaux.shared.firebase.authentication.ui

import com.oliviermarteaux.shared.firebase.authentication.domain.model.User

sealed class UserAuthState {
    data object Loading : UserAuthState()
    data class Connected(val user: User) : UserAuthState()
    data object NotConnected : UserAuthState()
}
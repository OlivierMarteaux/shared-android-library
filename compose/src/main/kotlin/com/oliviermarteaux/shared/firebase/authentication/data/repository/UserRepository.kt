package com.oliviermarteaux.shared.firebase.authentication.data.repository

import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val userAuthState: Flow<User?>

    suspend fun checkEmail(email: String): Result<Boolean>

    suspend fun createAccount(newUser: NewUser): Result<User?>

    suspend fun signIn(email: String, password: String): Result<User?>

    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    fun signOut(): Result<User?>

    suspend fun deleteAccount(): Result<User?>

    suspend fun signInWithGoogle(@StringRes serverClientIdStringRes: Int): Result<User?>
}
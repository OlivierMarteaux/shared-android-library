package com.oliviermarteaux.shared.firebase.authentication.data.service

import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * An interface for handling user authentication and account management.
 */
interface UserApi {
    val userAuthState: Flow<FirebaseUser?>
    suspend fun checkEmail(email: String): Result<Boolean>
    suspend fun createAccount(newUser: NewUser): Result<User?>
    suspend fun deleteAccount(): Result<User?>
    fun getUserById(id: String): Flow<Result<User?>>
    fun getAllUsers(): Flow<Result<List<User>>>
    suspend fun sendEmailVerificationLink()
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun signIn(email: String, password: String): Result<User?>
    suspend fun signInWithGoogle(idToken: String): Result<User?>
    fun signOut(): Result<User?>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun verifyEmail(): Result<User?>
}
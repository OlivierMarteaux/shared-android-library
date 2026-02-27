package com.oliviermarteaux.shared.firebase.authentication.data.repository

import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserApi
import com.oliviermarteaux.shared.firebase.authentication.domain.mapper.toUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing user data.
 *
 * @property userApi The API for interacting with user data.
 */
@Singleton
class UserFirebaseRepository @Inject constructor(private val userApi: UserApi): UserRepository {

    override fun getAllUsers(): Flow<Result<List<User>>> = userApi.getAllUsers()

    override suspend fun updateUser(user: User): Result<Unit> = userApi.updateUser(user)

    /**
     * A flow that emits the current authentication state of the user.
     * Emits a [FirebaseUser] if a user is signed in, or `null` otherwise.
     */
    override val userAuthState: Flow<User?> = userApi.userAuthState.map { firebaseUser ->
        firebaseUser?.toUser()
    }
    /**
     * Checks if an email address is already registered.
     *
     * @param email The email address to check.
     * @return A [Result] indicating whether the email exists. `Result.success(true)` if it exists, `Result.success(false)` otherwise.
     */
    override suspend fun checkEmail(email: String): Result<Boolean> = userApi.checkEmail(email)
    /**
     * Creates a new user account.
     *
     * @param newUser The details of the new user.
     * @return A [Result] containing the created [User] on success, or an error.
     */
    override suspend fun createAccount(newUser: NewUser): Result<User?> = userApi.createAccount(newUser)
    /**
     * Signs in a user with their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Result] containing the signed-in [User] on success, or an error.
     */
    override suspend fun signIn(email: String, password: String): Result<User?> =
        userApi.signIn(email, password)
    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the reset link to.
     * @return A [Result] indicating success or failure.
     */
    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        userApi.sendPasswordResetEmail(email)
    /**
     * Signs out the current user.
     *
     * @return A [Result] containing the signed-out [User] on success, or an error.
     */
    override fun signOut(): Result<User?> = userApi.signOut()
    /**
     * Deletes the current user's account.
     *
     * @return A [Result] containing the deleted [User] on success, or an error.
     */
    override suspend fun deleteAccount(): Result<User?> = userApi.deleteAccount()

    override suspend fun signInWithGoogle(@StringRes serverClientIdStringRes: Int): Result<User?> =
        userApi.signInWithGoogle(serverClientIdStringRes)
}
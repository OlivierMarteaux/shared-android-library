package com.oliviermarteaux.shared.firebase.authentication.data.repository

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

    override val userAuthState: Flow<User?> = userApi.userAuthState.map { firebaseUser ->
        firebaseUser?.toUser()
    }

    override suspend fun checkEmail(email: String): Result<Boolean> = userApi.checkEmail(email)

    override suspend fun createAccount(newUser: NewUser): Result<User?> = userApi.createAccount(newUser)

    override suspend fun deleteAccount(): Result<User?> = userApi.deleteAccount()

    override fun getUserById(id: String): Flow<Result<User?>> = userApi.getUserById(id)

    override fun getAllUsers(): Flow<Result<List<User>>> = userApi.getAllUsers()

    override suspend fun sendEmailVerificationLink() = userApi.sendEmailVerificationLink()

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        userApi.sendPasswordResetEmail(email)

    override suspend fun signIn(email: String, password: String): Result<User?> =
        userApi.signIn(email, password)

    override suspend fun signInWithGoogle(idToken: String): Result<User?> =
        userApi.signInWithGoogle(idToken)

    override fun signOut(): Result<User?> = userApi.signOut()

    override suspend fun updateUser(user: User): Result<Unit> = userApi.updateUser(user)

    override suspend fun verifyEmail(): Result<User?> = userApi.verifyEmail()
}
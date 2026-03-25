package com.oliviermarteaux.shared.firebase.authentication.data.service

import android.content.Context
import android.util.Log
import android.webkit.ConsoleMessage
import androidx.annotation.StringRes
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat.getString
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.oliviermarteaux.shared.firebase.authentication.domain.mapper.toUser
import com.oliviermarteaux.shared.firebase.firestore.domain.model.GameLevelStat
import com.oliviermarteaux.shared.firebase.authentication.domain.model.LoginMethod
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * A Firebase implementation of the [UserApi] interface.
 */
class UserFirebaseApi @Inject constructor(private val context: Context): UserApi {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = firebaseAuth.currentUser
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")
    private val credentialManager = CredentialManager.create(context)

    /**
     * A flow that emits the current authentication state of the user.
     * Emits a [FirebaseUser] if a user is signed in, or `null` otherwise.
     */
    override val userAuthState: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    //_ #############################################
    //_ # CHECK EMAIL
    //_ #############################################
    /**
     * Checks if an email address is already registered.
     *
     * @param email The email address to check.
     * @return A [Result] indicating whether the email exists. `Result.success(true)` if it exists, `Result.success(false)` otherwise.
     */
    override suspend fun checkEmail(email: String) = runCatching {
//        throw IllegalStateException("Forced exception for testing")
        val normalizedEmail: String = email.lowercase()
//        if (normalizedEmail.endsWith("gmail.com", ignoreCase = true)) return@runCatching false
        var emailExist: Boolean
        val snapshot = firestore.collection("users")
            .whereEqualTo("email", normalizedEmail)
            .get()
            .await()
        emailExist = !snapshot.isEmpty
        Log.d("OM_TAG", "UserFirebaseApi: checkEmail: emailExist =  $emailExist")
        emailExist
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: checkEmail: exception: ${e.message}")
    }

    //_ #############################################
    //_ # CHECK PSEUDO
    //_ #############################################
    /**
     * Checks if the pseudo is already used.
     *
     * @param [pseudo] The pseudo to check.
     * @return A [Boolean] indicating whether the pseudo exists.
     */
//    override suspend fun checkPseudo(pseudo: String): Result<Boolean> = runCatching {
//        var pseudoExist: Boolean
//        val snapshot = firestore.collection("users")
//            .whereEqualTo("pseudo", pseudo)
//            .get()
//            .await()
//        pseudoExist = !snapshot.isEmpty
//        Log.d("OM_TAG", "UserFirebaseApi: checkEmail: pseudoExist =  $pseudoExist")
//        pseudoExist
//    }.onFailure { e ->
//        FirebaseCrashlytics.getInstance().recordException(e)
//        Log.e("OM_TAG", "UserFirebaseApi: checkEmail: exception: ${e.message}")
//    }

    //_ #############################################
    //_ # CREATE ACCOUNT
    //_ #############################################
    /**
     * Creates a new user account.
     *
     * @param newUser The details of the new user.
     * @return A [Result] containing the created [User] on success, or an error.
     */
    override suspend fun createAccount(newUser: NewUser) : Result<User?> = runCatching {
//            throw IllegalStateException("Forced exception for testing")
            Log.d("OM_TAG", "UserFirebaseApi: CreateAccount: newUser = $newUser")
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(newUser.email, newUser.password)
                .await() // ✅ this suspends until Firebase finishes

            // Do follow-up work AFTER user is created :
            val firebaseUser = authResult.user
            firebaseUser?.let { uid ->
                // 1) Send verification email
                firebaseUser.sendEmailVerification().await()
                Log.d("OM_TAG", "UserFirebaseApi: CreateAccount: email verification sent")
                // 2) Update FirebaseUser profile (displayName)
                updateFirebaseUserProfile(newUser, firebaseUser)
                // 3) Add new user to Firestore
                addNewUserToFirestore(newUser, firebaseUser.uid, LoginMethod.EMAIL)
            }
            firebaseUser?.toUser(LoginMethod.EMAIL)
    }.onFailure{ e->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: CreateAccount: exception: ${e.message}",e)
    }

    override suspend fun sendEmailVerificationLink(){
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser?.reload()?.await()
        firebaseUser?.sendEmailVerification()?.await()
        Log.d("OM_TAG", "UserFirebaseApi: sendEmailVerificationLink: email verification sent")
    }

    override suspend fun verifyEmail(): Result<User?> = runCatching {
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser?.reload()?.await()
        if (firebaseUser?.isEmailVerified != true) {
            throw IllegalStateException("Email not verified")
        }
        Log.d("OM_TAG", "UserFirebaseApi: verifyEmail: firebaseUser.isEmailVerified = ${firebaseUser.isEmailVerified}")
        val user = firebaseUser.toUser(LoginMethod.EMAIL)
        updateUser(user)
        Log.d("OM_TAG", "UserFirebaseApi: verifyEmail: updated user = $user")
        user
    }.onFailure{ e->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: finalizeAccount: exception: ${e.message}",e)
    }

//    override suspend fun checkEmailVerification() = runCatching {
//        val firebaseUser = firebaseAuth.currentUser
//        firebaseUser?.reload()?.await()
//        if (firebaseUser?.isEmailVerified?:false) {} else {
//            firebaseUser?.sendEmailVerification()?.await()
//            throw IllegalStateException("Email not verified")
//        }
//    }.onFailure{ e->
//        FirebaseCrashlytics.getInstance().recordException(e)
//        Log.e("OM_TAG", "UserFirebaseApi: checkEmailVerification: exception: ${e.message}",e)
//    }

    //_ #############################################
    //_ # GET ALL USERS
    //_ #############################################

    override fun getAllUsers(): Flow<Result<List<User>>> = flow {

        val snapshot = usersCollection.get().await()

        val userList = snapshot.documents.mapNotNull {
            it.toObject(User::class.java)?.copy(id = it.id)
        }

        Log.d("OM_TAG", "UserFirebaseApi: getAllUsers: UserList.size = ${userList.size}")
        emit(
            Result.success(
                userList
            )
        )
    }.catch { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: getAllUsers: failed", e)
        emit(Result.failure(e))
    }

    //_ #############################################
    //_ # GET USER
    //_ #############################################

    override fun getUserById(id: String): Flow<Result<User?>> = flow {

        require(id.isNotBlank()) { "User ID must not be blank" }

        val snapshot = usersCollection
            .document(id)
            .get()
            .await()

        val currentUser = snapshot.toObject(User::class.java)

        emit(
            Result.success(
                currentUser
            )
        )
    }.catch { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e(
            "OM_TAG",
            "UserFirebaseApi: updateUser: failed due to Exception",
            e
        )
        emit(Result.failure(e))
    }

    //_ #############################################
    //_ # UPDATE USER
    //_ #############################################

    override suspend fun updateUser(user: User): Result<Unit> = runCatching {

        require(user.id.isNotBlank()) { "User ID must not be blank" }

        val updatedUser = user.copy(
            lastModifiedDate = Date()
        )

        usersCollection
            .document(user.id)
            .set(updatedUser, SetOptions.merge())
            .await()

        Unit
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e(
            "OM_TAG",
            "UserFirebaseApi: updateUser: failed due to Exception",
            e
        )
    }

//    override suspend fun updatePseudo(id: String, pseudo: String): Result<Unit> = runCatching {
//        usersCollection
//            .document(id)
//            .update(
//                mapOf(
//                    "pseudo" to pseudo,
//                    "lastModifiedDate" to Date()
//                )
//            )
//            .await()
//    }
//
//    override suspend fun updateGameStat(id: String, gameStat: List<GameLevelStat>): Result<Unit> = runCatching {
//        usersCollection
//            .document(id)
//            .update(
//                mapOf(
//                    "gameStat" to gameStat,
//                    "lastModifiedDate" to Date()
//                )
//            )
//            .await()
//    }

    //_ #############################################
    //_ # PRIVATE: ADD NEW USER
    //_ #############################################
    /**
     * Adds a new user to the Firestore database.
     *
     * @param newUser The new user's data.
     * @param uid The user's unique ID.
     */
    private suspend fun addNewUserToFirestore(newUser: NewUser, uid: String, loginMethod: LoginMethod) =
        try {
//            val userData: Map<String, String> = mapOf(
//                "id" to uid,
//                "firstname" to newUser.firstname,
//                "lastname" to newUser.lastname,
//                "fullname" to newUser.fullname,
//                "email" to newUser.email,
//                "photoUrl" to newUser.photoUrl,
//                "pseudo" to newUser.pseudo,
//                "timestamp" to newUser.timestamp.toString(),
//                "creationDate" to newUser.creationDate.toString(),
//                "accountType" to newUser.accountType.name
//            )
            val user: User = newUser.toUser(id = uid, loginMethod = loginMethod)
            Log.d("OM_TAG", "UserFirebaseApi: CreateAccount: addNewUserToFirestore: user = $user")
            firestore.collection("users").document(uid)
                .set(user, SetOptions.merge())
                .await()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.e("OM_TAG", "UserFirebaseApi: CreateAccount: addNewUserToFirestore exception: ${e.message}")
        }

    //_ #############################################
    //_ # UPDATE FIREBASE USER
    //_ #############################################
    /**
     * Updates the user's profile in Firebase Authentication.
     *
     * @param newUser The new user's data.
     * @param firebaseUser The Firebase user to update.
     */
    private suspend fun updateFirebaseUserProfile(newUser: NewUser, firebaseUser: FirebaseUser) =
        try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(with(newUser) { getComputedFullName() })
                .build()
            firebaseUser.updateProfile(profileUpdates).await()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.e("OM_TAG", "UserFirebaseApi: CreateAccount: updateFirebaseUserProfile exception: ${e.message}")
        }

    //_ #############################################
    //_ # SIGN IN WITH EMAIL
    //_ #############################################
    /**
     * Signs in a user with their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Result] containing the signed-in [User] on success, or an error.
     */
    override suspend fun signIn(email: String, password: String): Result<User?> = runCatching {
//        throw IllegalStateException("Forced exception for testing")
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser uid: ${firebaseUser?.uid}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser email: ${firebaseUser?.email}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser providerData: ${firebaseUser?.providerData}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser isAnonymous: ${firebaseUser?.isAnonymous}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser metadata: ${firebaseUser?.metadata}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser multiFactor: ${firebaseUser?.multiFactor}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser tenantId: ${firebaseUser?.tenantId}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser isEmailVerified: ${firebaseUser?.isEmailVerified}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser phoneNumber: ${firebaseUser?.phoneNumber}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser providerId: ${firebaseUser?.providerId}")

        if (firebaseUser?.isEmailVerified != true) {
            firebaseUser?.sendEmailVerification()
            Log.d("OM_TAG", "UserFirebaseApi:signIn: email verification link sent")
            throw IllegalStateException("Email not verified")
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            firestore.collection("users").document(firebaseUser?.uid ?:"").update("fcmToken", token)
        }
        Log.d("OM_TAG", "UserFirebaseApi:signIn: success")
        firebaseUser?.toUser(LoginMethod.EMAIL)
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi:signIn: exception: ${e.message}")
    }

    //_ #############################################
    //_ # SIGN IN WITH GOOGLE
    //_ #############################################
    /**
     * Launch Google Sign-In using Credential Manager
     * Returns the signed-in FirebaseUser or null
     */
    override suspend fun signInWithGoogle(idToken: String): Result<User?> = runCatching {

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential).await()

        val firebaseUser = firebaseAuth.currentUser
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser uid: ${firebaseUser?.uid}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser email: ${firebaseUser?.email}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser providerData: ${firebaseUser?.providerData}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser isAnonymous: ${firebaseUser?.isAnonymous}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser metadata: ${firebaseUser?.metadata}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser multiFactor: ${firebaseUser?.multiFactor}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser tenantId: ${firebaseUser?.tenantId}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser isEmailVerified: ${firebaseUser?.isEmailVerified}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser phoneNumber: ${firebaseUser?.phoneNumber}")
        Log.d("OM_TAG", "UserFirebaseApi:signIn: firebaseUser providerId: ${firebaseUser?.providerId}")

        val user = firebaseUser?.toUser(LoginMethod.GOOGLE)

        val snapshot = firestore.collection("users")
            .whereEqualTo("id", user?.id)
            .get()
            .await()

        val isNewAccount = snapshot.isEmpty

        if (isNewAccount && firebaseUser != null) {
            val newUser = NewUser(
                firstname = user?.firstname ?: "",
                lastname = user?.lastname ?: "",
                fullname = user?.fullname ?: "",
                email = user?.email ?: "",
                photoUrl = user?.photoUrl ?: "",
                pseudo = user?.pseudo ?: "",
            )

            addNewUserToFirestore(newUser, firebaseUser.uid, LoginMethod.GOOGLE)
        }

        user
    }
//    override suspend fun signInWithGoogle(
//        @StringRes serverClientIdStringRes: Int
//    ): Result<User?> = runCatching {
//
//        // This variable will hold the credential from either the silent or interactive attempt.
//        val credential = try {
//            // --- ATTEMPT 1: SILENT SIGN-IN ---
//            Log.d("OM_TAG", "UserFirebaseApi::signInWithGoogle: Attempting silent sign-in...")
//            val silentSignInOption = GetGoogleIdOption.Builder()
//                .setServerClientId(getString(context, serverClientIdStringRes))
//                .setFilterByAuthorizedAccounts(true) // <<< Key for silent sign-in
//                .setAutoSelectEnabled(true)
//                .build()
//
//            val silentRequest = GetCredentialRequest.Builder()
//                .addCredentialOption(silentSignInOption)
//                .build()
//
//            credentialManager.getCredential(context, silentRequest).credential
//
//        } catch (e: NoCredentialException) {
//            // --- FALLBACK: INTERACTIVE SIGN-IN ---
//            // This is an expected failure for new users. Fall back to the interactive flow.
//            Log.d("OM_TAG", "UserFirebaseApi::signInWithGoogle: Silent sign-in failed, falling back to interactive.")
//            val interactiveSignInOption = GetGoogleIdOption.Builder()
//                .setServerClientId(getString(context, serverClientIdStringRes))
//                .setFilterByAuthorizedAccounts(false) // <<< Key for interactive sign-in
//                .build()
//
//            val interactiveRequest = GetCredentialRequest.Builder()
//                .addCredentialOption(interactiveSignInOption)
//                .build()
//
//            credentialManager.getCredential(context, interactiveRequest).credential
//        }
//
//        // Check if credential is of type Google ID
//        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//            // Create Google ID Token
//            val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
//            // Sign-in using credential
//            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//            firebaseAuth.signInWithCredential(firebaseCredential).await()
//            // Sign in success, return the signed-in user
//            Log.d("OM_TAG", "UserFirebaseApi::signInWithGoogle: signInWithCredential:success")
//            val firebaseUser = firebaseAuth.currentUser
//            // Do follow-up work AFTER user is created :
//            val user: User? = firebaseUser?.toUser()
//
//            var isNewAccount: Boolean
//            val snapshot = firestore.collection("users")
//                .whereEqualTo("id", user?.id)
//                .get()
//                .await()
//            isNewAccount = snapshot.isEmpty
//
//            if (isNewAccount) {
//                val newUser: NewUser = NewUser(
//                    firstname = user?.firstname ?: "",
//                    lastname = user?.lastname ?: "",
//                    fullname = user?.fullname ?: "",
//                    email = user?.email ?: "",
//                    photoUrl = user?.photoUrl ?: "",
//                    pseudo = user?.pseudo ?: "",
//                )
//                firebaseUser?.let { uid ->
//                    // 1) Update FirebaseUser profile (displayName)
//                    // updateFirebaseUserProfile(newUser, firebaseUser)
//                    // 2) Add new user to Firestore
//                    addNewUserToFirestore(newUser, firebaseUser.uid, LoginMethod.GOOGLE)
//                }
//            }
//            user
//        } else {
//            Log.e("OM_TAG", "UserFirebaseApi::signInWithGoogle: Credential is not of type Google ID!")
//            return Result.failure(IllegalStateException("signInWithGoogle: Credential is not of type Google ID!"))
//        }
//    }.onFailure { e ->
//        FirebaseCrashlytics.getInstance().recordException(e)
//        Log.e("OM_TAG", "UserFirebaseApi::signInWithGoogle: signInWithGoogle:failure", e)
//    }

    //_ #############################################
    //_ # SEND PASSWORD RESET
    //_ #############################################
    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the reset link to.
     * @return A [Result] indicating success or failure.
     */
    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> = runCatching {
//        throw IllegalStateException("Forced exception for testing")
        firebaseAuth.sendPasswordResetEmail(email).await()
        Log.d("OM_TAG", "ResetViewModel: sendPasswordResetEmail($email): Password reset email sent")
        Unit
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "ResetViewModel: sendPasswordResetEmail($email): Password reset failed: ${e.message}", e)
    }

    //_ #############################################
    //_ # SIGN OUT
    //_ #############################################
    /**
     * Signs out the current user.
     *
     * @return A [Result] containing the signed-out [User] on success, or an error.
     */
    override fun signOut() : Result<User?> = runCatching {
//        throw IllegalStateException("Forced exception for testing")
        Log.d("OM_TAG", "UserFirebaseApi: signOut(): Signing out")
        firebaseAuth.signOut()
        null
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: signOut(): Failed to sign out: ${e.message}")
    }

    //_ #############################################
    //_ # DELETE ACCOUNT
    //_ #############################################
    /**
     * Deletes the current user's account.
     *
     * @return A [Result] containing the deleted [User] on success, or an error.
     */
    override suspend fun deleteAccount(): Result<User?> = runCatching {
//        throw IllegalStateException("Forced exception for testing")
        deleteFireStoreUserEntry()
        deleteAuthUser()
        signOut()
        null
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: deleteAccount(): Failed to delete account: ${e.message}")
    }

    //_ #############################################
    //_ # PRIVATE: DELETE AUTH USER
    //_ #############################################
    /**
     * Deletes the authenticated user from Firebase Authentication.
     */
    private suspend fun deleteAuthUser() = runCatching {
        Log.d("OM_TAG", "UserFirebaseApi: deleteAuthUser(): Deleting auth user")
        user?.delete()?.await()
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: deleteAuthUser(): Failed to delete auth user: ${e.message}")
    }
    //_ #############################################
    //_ # PRIVATE: DELETE FIRESTORE ENTRY
    //_ #############################################
    /**
     * Deletes the user's entry from the Firestore "users" collection.
     */
    private suspend fun deleteFireStoreUserEntry() = runCatching {
        val userUid = user?.uid
        userUid?.let {
            firestore.collection("users").document(userUid)
                .delete().await()
        }
        Log.d("OM_TAG", "UserFirebaseApi: deleteFireStoreUserEntry(): userUid = $userUid")
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: deleteFireStoreUserEntry(): Failed to delete user: ${e.message}")
    }
}

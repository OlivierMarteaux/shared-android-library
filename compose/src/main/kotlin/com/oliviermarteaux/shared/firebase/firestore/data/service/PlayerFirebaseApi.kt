package com.oliviermarteaux.shared.firebase.firestore.data.service

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Player
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date

class PlayerFirebaseApi : PlayerApi {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val playersCollection = firestore.collection("players")

    override suspend fun checkPseudoAvailability(pseudo: String): Result<Boolean> = runCatching {
        var pseudoExist: Boolean
        val snapshot = playersCollection
            .whereEqualTo("pseudo", pseudo)
            .get()
            .await()
        pseudoExist = snapshot.isEmpty
        Log.d("OM_TAG", "PlayerFirebaseApi: checkPseudo: pseudoExist =  $pseudoExist")
        pseudoExist
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "PlayerFirebaseApi: checkPseudo: exception: ${e.message}")
    }

    override suspend fun createNewPlayer(player: Player) : Result<Player> = runCatching {

        val uid = requireNotNull(firebaseAuth.uid) {
            "User must be authenticated to create new player"
        }
        val newPlayer = Player(id = uid, pseudo = player.pseudo)
        playersCollection
            .document(uid)
            .set(newPlayer)
            .await()
        Log.d("OM_TAG", "PlayerFirebaseApi: createNewPlayer: success")
        newPlayer
    }.onFailure { e ->
        Log.e("OM_TAG", "PlayerFirebaseApi: createNewPlayer: failed due to Exception: ${e.message}")
    }

    override suspend fun getCurrentPlayer() : Flow<Result<Player?>> = flow {

        val uid = requireNotNull(firebaseAuth.uid) {
            "User must be authenticated to fetch current player"
        }
        val snapshot = playersCollection
            .document(uid)
            .get()
            .await()
        val currentPlayer: Player? = snapshot.toObject(Player::class.java)

        emit(
            Result.success(
                currentPlayer
            )
        )
    }.catch { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e(
            "OM_TAG",
            "PlayerFirebaseApi: getCurrentPlayer: failed due to Exception",
            e
        )
        emit(Result.failure(e))
    }

    override suspend fun updatePlayer(player: Player): Result<Player> = runCatching {

        require(player.id.isNotBlank()) { "Player ID must not be blank" }

        val updatedPlayer = player.copy(
            lastModifiedDate = Date()
        )

        playersCollection
            .document(player.id)
            .set(updatedPlayer, SetOptions.merge())
            .await()

        updatedPlayer
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e(
            "OM_TAG",
            "PlayerFirebaseApi::updatePlayer: failed due to Exception",
            e
        )
    }
}
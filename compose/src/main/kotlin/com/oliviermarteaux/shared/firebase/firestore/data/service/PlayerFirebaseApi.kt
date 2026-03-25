package com.oliviermarteaux.shared.firebase.firestore.data.service

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Player
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import kotlinx.coroutines.tasks.await

class PlayerFirebaseApi : PlayerApi {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val playersCollection = firestore.collection("players")

    override suspend fun checkPseudo(pseudo: String): Result<Boolean> = runCatching {
        var pseudoExist: Boolean
        val snapshot = playersCollection
            .whereEqualTo("pseudo", pseudo)
            .get()
            .await()
        pseudoExist = !snapshot.isEmpty
        Log.d("OM_TAG", "PlayerFirebaseApi: checkPseudo: pseudoExist =  $pseudoExist")
        pseudoExist
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "PlayerFirebaseApi: checkPseudo: exception: ${e.message}")
    }

    override suspend fun createNewPlayer(currentUserUid: String, pseudo: String) : Result<Unit> = runCatching {

        val newPlayer = Player(id = currentUserUid, pseudo = pseudo)
        playersCollection.add(newPlayer).await()
        Log.d("OM_TAG", "PlayerFirebaseApi: createNewPlayer: success")
        Unit
    }.onFailure { e ->
        Log.e("OM_TAG", "PlayerFirebaseApi: createNewPlayer: failed due to Exception: ${e.message}")
    }
}
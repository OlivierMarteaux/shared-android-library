package com.oliviermarteaux.shared.firebase.firestore.data.service

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
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
        Log.d("OM_TAG", "UserFirebaseApi: checkEmail: pseudoExist =  $pseudoExist")
        pseudoExist
    }.onFailure { e ->
        FirebaseCrashlytics.getInstance().recordException(e)
        Log.e("OM_TAG", "UserFirebaseApi: checkEmail: exception: ${e.message}")
    }
}
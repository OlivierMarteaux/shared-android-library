package com.oliviermarteaux.shared.firebase.firestore.data.repository

interface PlayerRepository {

    suspend fun checkPseudo(pseudo: String): Result<Boolean>

    suspend fun createNewPlayer(currentUserUid: String, pseudo: String) : Result<Unit>

}
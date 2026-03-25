package com.oliviermarteaux.shared.firebase.firestore.data.service

interface PlayerApi {

    suspend fun checkPseudo(pseudo: String): Result<Boolean>

}
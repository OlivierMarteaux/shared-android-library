package com.oliviermarteaux.shared.firebase.firestore.data.repository

import com.oliviermarteaux.shared.firebase.firestore.data.service.PlayerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerFirebaseRepository @Inject constructor(
    private val playerApi: PlayerApi,
): PlayerRepository {

    override suspend fun checkPseudo(pseudo: String): Result<Boolean> =
        playerApi.checkPseudo(pseudo)
}
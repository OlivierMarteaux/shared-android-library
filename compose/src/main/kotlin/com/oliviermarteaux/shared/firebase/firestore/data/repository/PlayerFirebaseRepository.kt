package com.oliviermarteaux.shared.firebase.firestore.data.repository

import com.oliviermarteaux.shared.firebase.firestore.data.service.PlayerApi
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerFirebaseRepository @Inject constructor(
    private val playerApi: PlayerApi,
): PlayerRepository {

    override suspend fun checkPseudoAvailability(pseudo: String): Result<Boolean> =
        playerApi.checkPseudoAvailability(pseudo)

    override suspend fun createNewPlayer(player: Player) : Result<Player> =
        playerApi.createNewPlayer(player)

    override fun getAllPlayers(): Flow<Result<List<Player>>> =
        playerApi.getAllPlayers()

    override suspend fun getCurrentPlayer() : Flow<Result<Player?>> =
        playerApi.getCurrentPlayer()

    override suspend fun updatePlayer(player: Player): Result<Player> =
        playerApi.updatePlayer(player)

}
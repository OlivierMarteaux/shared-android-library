package com.oliviermarteaux.shared.firebase.firestore.data.repository

import com.oliviermarteaux.shared.firebase.firestore.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun checkPseudoAvailability(pseudo: String): Result<Boolean>
    suspend fun createNewPlayer(player: Player) : Result<Player>

    fun getAllPlayers(): Flow<Result<List<Player>>>
    suspend fun getCurrentPlayer() : Flow<Result<Player?>>
    suspend fun updatePlayer(player: Player): Result<Player>

}
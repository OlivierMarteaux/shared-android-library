package com.oliviermarteaux.shared.firebase.firestore.domain.model

import com.google.android.gms.common.internal.AccountType
import com.oliviermarteaux.shared.extensions.toDate
import com.oliviermarteaux.shared.firebase.firestore.domain.model.GameLevel
import com.oliviermarteaux.shared.firebase.firestore.domain.model.GameLevelStat
import com.oliviermarteaux.shared.firebase.authentication.domain.model.LoginMethod
import java.io.Serializable
import java.time.LocalDate
import java.util.Date
import kotlin.text.ifEmpty

/**
 * This class represents a User data object. It holds basic information about a user, including
 * their ID, first name, and last name. The class implements Serializable to allow for potential
 * serialization needs.
 */
data class Player(

    val id: String = "",
    val pseudo: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val creationDate: Date = Date(),
    val lastModifiedDate: Date = Date(),
    val gameStat: List<GameLevelStat> = emptyList()

) : Serializable {

    // Explicit no-arg constructor for Firebase deserialization --> needed for minification
    constructor() : this(
        id = "",
        pseudo = "",
        timestamp = 0,
        creationDate = Date(),
        lastModifiedDate = Date(),
        gameStat = emptyList()
    )

    val totalScore: Long
        get() = if(gameStat.isEmpty()) -1 else gameStat.filter{ it.score >=0 }.sumOf{ it.score }

    val totalPlayTime: Long
        get() = if(gameStat.isEmpty()) -1 else gameStat.sumOf{ it.playTime}

    fun getCompletedGameScore(lastLevel: GameLevel): Long =
        totalScore.takeIf { (gameStat.find { it.level == lastLevel }?.score ?: -1) >= 0 } ?: -1

    fun scoreForLevel(level: GameLevel): Long =
        gameStat.find { it.level == level }?.score?.takeIf { it >= 0 }?:-1
}

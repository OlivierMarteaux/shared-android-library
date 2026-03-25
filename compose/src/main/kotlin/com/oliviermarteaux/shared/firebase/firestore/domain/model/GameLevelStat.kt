package com.oliviermarteaux.shared.firebase.firestore.domain.model

import java.io.Serializable

data class GameLevelStat (
    val level: GameLevel = GameLevel.LEVEL1,
    val score: Long = -1,
    val playTime: Long = 0
): Serializable

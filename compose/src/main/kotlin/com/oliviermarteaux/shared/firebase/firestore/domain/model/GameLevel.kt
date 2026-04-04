package com.oliviermarteaux.shared.firebase.firestore.domain.model

import com.oliviermarteaux.shared.compose.R

enum class GameLevel(val id: Int, val labelRes: Int) {
    ALL(0, R.string.all_levels),
    LEVEL1(1, R.string.level_1),
    LEVEL2(2, R.string.level_2),
    LEVEL3(3, R.string.level_3),
    LEVEL4(4, R.string.level_4),
    LEVEL5(5, R.string.level_5),
    LEVEL6(6, R.string.level_6),
    LEVEL7(7, R.string.level_7),
    LEVEL8(8, R.string.level_8),
    LEVEL9(9, R.string.level_9),
    LEVEL10(10, R.string.level_10)
}
package com.oliviermarteaux.shared.utils

fun randomAvatarUrl(): String {
    val genders = listOf("men", "women")
    val gender = genders.random()
    val id = (0..99).random()  // RandomUser has portraits from 0 to 99

    return "https://randomuser.me/api/portraits/$gender/$id.jpg"
}
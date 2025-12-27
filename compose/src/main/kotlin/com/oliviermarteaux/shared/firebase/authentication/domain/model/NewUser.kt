package com.oliviermarteaux.shared.firebase.authentication.domain.model

/**
 * Represents a new user to be created.
 */
data class NewUser(
    val firstname: String = "",
    val lastname: String = "",
    val fullname: String = "",
    val email: String = "",
    val password: String = "",
    val photoUrl: String = ""
){
    fun getComputedFullName() = fullname.ifEmpty {
        listOf(firstname, lastname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }
}

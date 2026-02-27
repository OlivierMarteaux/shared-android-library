package com.oliviermarteaux.shared.firebase.authentication.domain.model

import java.io.Serializable
import kotlin.text.ifEmpty

/**
 * This class represents a User data object. It holds basic information about a user, including
 * their ID, first name, and last name. The class implements Serializable to allow for potential
 * serialization needs.
 */
data class User(

    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val fullname: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val pseudo: String = "",
    val score: Long = -1

) : Serializable {

    // Explicit no-arg constructor for Firebase deserialization --> needed for minification
    constructor() : this(
        id = "",
        firstname = "",
        lastname = "",
        fullname = "",
        email = "",
        photoUrl = "",
        pseudo = "",
        score = -1
    )

    fun getComputedFullName() = fullname.ifEmpty {
        listOf(firstname, lastname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }
}

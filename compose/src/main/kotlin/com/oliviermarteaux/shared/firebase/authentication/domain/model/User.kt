package com.oliviermarteaux.shared.firebase.authentication.domain.model

import com.google.android.gms.common.internal.AccountType
import com.oliviermarteaux.shared.extensions.toDate
import com.oliviermarteaux.shared.firebase.firestore.domain.model.GameLevel
import com.oliviermarteaux.shared.firebase.firestore.domain.model.GameLevelStat
import java.io.Serializable
import java.time.LocalDate
import java.util.Date
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
    val timestamp: Long = System.currentTimeMillis(),
    val creationDate: Date = Date(),
    val lastModifiedDate: Date = Date(),
    val loginMethod: LoginMethod = LoginMethod.UNKNOWN,

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
        timestamp = 0,
        creationDate = Date(),
        lastModifiedDate = Date(),
        loginMethod = LoginMethod.UNKNOWN,
    )

    fun getComputedFullName() = fullname.ifEmpty {
        listOf(firstname, lastname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }
}

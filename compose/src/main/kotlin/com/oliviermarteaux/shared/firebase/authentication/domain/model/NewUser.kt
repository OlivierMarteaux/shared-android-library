package com.oliviermarteaux.shared.firebase.authentication.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.common.internal.AccountType
import com.oliviermarteaux.shared.extensions.toDate
import java.time.LocalDate
import java.util.Date

/**
 * Represents a new user to be created.
 */
data class NewUser (
    val firstname: String = "",
    val lastname: String = "",
    val fullname: String = "",
    val email: String = "",
    val password: String = "",
    val photoUrl: String = "",
    val pseudo: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val creationDate: Date = Date(),
    val lastModifiedDate: Date = Date(),
    val loginMethod: LoginMethod = LoginMethod.UNKNOWN
){
    fun getComputedFullName() = fullname.ifEmpty {
        listOf(firstname, lastname)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }
}

package com.oliviermarteaux.shared.firebase.authentication.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User

/**
 * Converts a [FirebaseUser] object to a [User] object.
 *
 * @return A [User] object.
 */
fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        firstname = displayName?.substringBefore(" ") ?: "",
        lastname = displayName?.substringAfter(" ") ?: "",
        fullname = displayName ?: "",
        email = email ?: "",
        photoUrl = photoUrl?.toString() ?: ""
    )
}
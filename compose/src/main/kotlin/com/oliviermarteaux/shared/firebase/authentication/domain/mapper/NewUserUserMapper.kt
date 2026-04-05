package com.oliviermarteaux.shared.firebase.authentication.domain.mapper

import com.oliviermarteaux.shared.firebase.authentication.domain.model.LoginMethod
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User

/**
 * Converts a [NewUser] object to a [User] object.
 *
 * @return A [User] object.
 */
fun NewUser.toUser(id: String, loginMethod: LoginMethod): User {
    return User(
        id = id,
        firstname = firstname,
        lastname = lastname,
        fullname = fullname,
        email = email,
        photoUrl = photoUrl,
        pseudo = pseudo,
        timestamp = timestamp,
        creationDate = creationDate,
        lastModifiedDate = lastModifiedDate,
        loginMethod = loginMethod,
    )
}
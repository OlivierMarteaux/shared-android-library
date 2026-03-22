package com.oliviermarteaux.shared.firebase.authentication.domain.mapper

import android.R.attr.accountType
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.firebase.authentication.domain.model.LoginMethod

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
package com.oliviermarteaux.shared.firebase.firestore.domain.model

import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import java.io.Serializable

/**
 * Represents a comment on a post.
 *
 * @property author The author of the comment.
 * @property content The content of the comment.
 * @property timestamp The time the comment was posted.
 */
data class Comment(
    val author: User = User(),
    val content: String = "",
    val timestamp: Long = 0L,
) : Serializable
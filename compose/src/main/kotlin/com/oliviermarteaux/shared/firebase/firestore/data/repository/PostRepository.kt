package com.oliviermarteaux.shared.firebase.firestore.data.repository

import com.oliviermarteaux.shared.firebase.firestore.domain.model.Comment
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    /**
     * Retrieves a Flow object containing a list of Posts ordered by creation date
     * in descending order.
     *
     * @return Flow containing a list of Posts.
     */
    val posts: Flow<Result<List<Post>>>

    /**
     * Adds a new Post to the data source using the injected PostApi.
     *
     * @param post The Post object to be added.
     */
    suspend fun addPost(post: Post): Result<Unit>

    /**
     * Adds a new comment to the data source using the injected PostApi.
     *
     * @param comment The comment to be added.
     * @param postId The ID of the post associated with the comment.
     */
    suspend fun addComment(postId: String, comment: Comment): Result<Unit>
}

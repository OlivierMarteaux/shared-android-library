package com.oliviermarteaux.shared.firebase.firestore.data.repository

import com.oliviermarteaux.shared.firebase.firestore.data.service.PostApi
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Comment
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class provides a repository for accessing and managing Post data.
 * It utilizes dependency injection to retrieve a PostApi instance for interacting
 * with the data source. The class is marked as a Singleton using @Singleton annotation,
 * ensuring there's only one instance throughout the application.
 */
@Singleton
class PostFirebaseRepository @Inject constructor(
    private val postApi: PostApi,
): PostRepository {
    /**
    * Retrieves a Flow object containing a list of Posts ordered by creation date
    * in descending order.
    *
    * @return Flow containing a list of Posts.
    */
    override val posts: Flow<Result<List<Post>>> = postApi.getPostsOrderByCreationDateDesc()

    /**
    * Adds a new Post to the data source using the injected PostApi.
    *
    * @param post The Post object to be added.
    */
    override suspend fun addPost(post: Post): Result<Unit> = postApi.addPost(post)

    /**
     * Adds a new comment to the data source using the injected PostApi.
     *
     * @param comment The comment to be added.
     * @param postId The ID of the post associated with the comment.
     */
    override suspend fun addComment(postId: String, comment: Comment): Result<Unit> = postApi.addComment(postId, comment)
}

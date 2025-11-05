package com.oliviermarteaux.shared.data

import kotlinx.coroutines.flow.Flow

/**
 * A generic interface that defines a repository for managing data of type [T].
 * Provides methods for updating items, retrieving data streams, and observing
 * individual items by their ID.
 *
 * ### Type Parameter:
 * @param T The type of the data entity managed by this repository.
 *
 * ### Methods:
 * - [updateItem]: Updates an existing item in the repository and returns the updated item.
 * - [getDataStream]: Returns a [Flow] emitting a [List] of all items wrapped in a [Result].
 * - [getItemByIdStream]: Returns a [Flow] emitting the item with the given ID.
 *
 * ### Example Usage:
 * ```kotlin
 * class UserRepository : DataRepository<User> {
 *     override suspend fun updateItem(item: User): User { ... }
 *     override suspend fun getDataStream(): Result<Flow<List<User>>> { ... }
 *     override fun getItemByIdStream(id: Int): Flow<User> { ... }
 * }
 * ```
 */
interface DataRepository<T> {

    suspend fun updateItem(item: T): T

    suspend fun getDataStream(): Result<Flow<List<T>>>

    fun getItemByIdStream(id:Int): Flow<T>

}
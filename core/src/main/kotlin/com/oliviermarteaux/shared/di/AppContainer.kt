package com.oliviermarteaux.shared.di

import com.oliviermarteaux.shared.data.DataRepository

/**
 * A generic interface representing an application-level container that provides
 * dependencies or services required by the app.
 *
 * ### Type Parameter:
 * @param T The type of data entity managed by the contained [DataRepository].
 *
 * ### Properties:
 * - [dataRepository]: Provides access to a [DataRepository] for type [T].
 *
 * ### Example Usage:
 * ```kotlin
 * class AppContainerImpl : AppContainer<User> {
 *     override val dataRepository: DataRepository<User> = UserRepository()
 * }
 * ```
 */
interface AppContainer<T> {

    val dataRepository: DataRepository<T>
}
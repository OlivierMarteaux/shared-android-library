package com.oliviermarteaux.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides a set of [CoroutineDispatcher]s for managing coroutines on different threads.
 *
 * This class is useful for dependency injection or testing, allowing you to swap dispatchers
 * (e.g., for unit tests) while keeping the code that launches coroutines unchanged.
 *
 * ### Properties:
 * @property io Dispatcher optimized for offloading blocking IO tasks. Defaults to [Dispatchers.IO].
 * @property main Dispatcher confined to the main thread for UI interactions. Defaults to [Dispatchers.Main].
 * @property default Dispatcher used for CPU-intensive tasks. Defaults to [Dispatchers.Default].
 *
 * ### Example Usage:
 * ```kotlin
 * val dispatcherProvider = CoroutineDispatcherProvider()
 * viewModelScope.launch(dispatcherProvider.io) {
 *     // perform network or database operations
 * }
 * ```
 *
 * ### Notes:
 * - Can be injected into classes (like ViewModels) for easier testing with test dispatchers.
 */
class CoroutineDispatcherProvider(
    val io: CoroutineDispatcher = Dispatchers.IO,
    val main: CoroutineDispatcher = Dispatchers.Main,
    val default: CoroutineDispatcher = Dispatchers.Default,
)
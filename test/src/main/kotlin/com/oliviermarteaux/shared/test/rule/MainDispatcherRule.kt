package com.oliviermarteaux.shared.test.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * JUnit [org.junit.rules.TestWatcher] rule for setting the main [CoroutineDispatcher] to a test dispatcher.
 *
 * This rule is useful for testing coroutines in a deterministic way by replacing
 * `Dispatchers.Main` with a [kotlinx.coroutines.test.TestDispatcher] during test execution.
 * It ensures that the main dispatcher is properly set before a test starts and
 * reset after the test finishes.
 *
 * ### Constructor:
 * @param testDispatcher The [kotlinx.coroutines.test.TestDispatcher] to use as the main dispatcher. Defaults to [kotlinx.coroutines.test.StandardTestDispatcher].
 *
 * ### Properties:
 * @property testScope A [kotlinx.coroutines.test.TestScope] associated with the [testDispatcher] for running test coroutines.
 *
 * ### Behavior:
 * - `starting(description: Description)` sets `Dispatchers.Main` to the [testDispatcher] before each test.
 * - `finished(description: Description)` resets `Dispatchers.Main` to its original dispatcher after each test.
 *
 * ### Example Usage:
 * ```kotlin
 * @get:Rule
 * val mainDispatcherRule = MainDispatcherRule()
 *
 * @Test
 * fun testCoroutineFunction() = mainDispatcherRule.testScope.runTest {
 *     // Your coroutine test code here
 * }
 * ```
 *
 * This rule allows coroutines that normally run on the main dispatcher to be
 * executed in a controlled test environment, making it easier to write deterministic tests.
 */
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    val testScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
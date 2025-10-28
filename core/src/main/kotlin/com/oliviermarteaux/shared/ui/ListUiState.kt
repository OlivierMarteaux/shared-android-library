package com.oliviermarteaux.shared.ui

/**
 * Represents the UI state for loading and displaying data in a generic way.
 *
 * @param T The type of items being displayed (e.g., Applicant, Item, etc.).
 */
sealed interface ListUiState<out T> {

    /** State representing successful data load with a list of items. */
    data class Success<T>(val data: List<T>) : ListUiState<T>

    /** State representing an error during data loading. */
    data class Error(val throwable: Throwable? = null) : ListUiState<Nothing>

    /** State representing that data is currently loading. */
    data object Loading : ListUiState<Nothing>

    /** State representing that there are no items to display. */
    data object Empty : ListUiState<Nothing>
}
package com.drygin.popcornplan.common.ui

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
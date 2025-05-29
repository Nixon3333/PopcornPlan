package com.drygin.popcornplan.common.utils

import android.util.Log
import com.drygin.popcornplan.common.ui.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
suspend fun <T> Flow<Result<T>>.collectToUiState(stateFlow: MutableStateFlow<UiState<T>>) {
    this
        .onStart { stateFlow.value = UiState.Loading }
        .catch {
                e -> stateFlow.value = UiState.Error(e.stackTraceToString())
            Log.e("HomeScreenViewModel", "collectToUiState error: ${e.stackTraceToString()}")
        }
        .collect { result ->
            result
                .onSuccess { movies -> stateFlow.value = UiState.Success(movies) }
                .onFailure {
                        e -> stateFlow.value = UiState.Error(e.stackTraceToString())
                    Log.e("HomeScreenViewModel", "collectToUiState error: ${e.stackTraceToString()}")
                }
        }
}
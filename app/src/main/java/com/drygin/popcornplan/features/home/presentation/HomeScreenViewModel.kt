package com.drygin.popcornplan.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.features.home.domain.model.Movie
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: IMovieRepository
): ViewModel() {

    private val _newReleasesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val newReleasesState = _newReleasesState.asStateFlow()

    private val _trendingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingState = _trendingState.asStateFlow()

    private val _recommendationsState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val recommendationsState = _recommendationsState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    init {
        fetchNewMovies()
        //refresh()
    }
    fun fetchNewMovies() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                val newReleaseDeferred = async {
                    repository.getNewMovies().collectToUiState(_newReleasesState)
                }
                val trendingDeferred = async {
                    repository.getTrendingMovies().collectToUiState(_trendingState)
                }
                val recommendationDeferred = async {
                    repository.getRecommendationMovies().collectToUiState(_recommendationsState)
                }
                awaitAll(newReleaseDeferred, trendingDeferred, recommendationDeferred)
            }  catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching movies", e)
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun Flow<Result<List<Movie>>>.collectToUiState(stateFlow: MutableStateFlow<UiState<List<Movie>>>) {
        this
            .onStart { stateFlow.value = UiState.Loading }
            .catch { e -> stateFlow.value = UiState.Error(e.stackTraceToString()) }
            .collect { result ->
                result
                    .onSuccess { movies -> stateFlow.value = UiState.Success(movies) }
                    .onFailure { e -> stateFlow.value = UiState.Error(e.stackTraceToString()) }
            }
    }

    /*fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Вызов API
            delay(1000) // Имитация
            _trending.value = sampleMovies().shuffled().take(5)
            _newReleases.value = sampleMovies().shuffled().take(5)
            _recommendations.value = sampleMovies().shuffled().take(5)
            _isRefreshing.value = false
        }
    }*/
}
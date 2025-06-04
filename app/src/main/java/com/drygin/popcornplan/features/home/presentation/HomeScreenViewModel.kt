package com.drygin.popcornplan.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.utils.collectToUiState
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    private val _newReleasesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val newReleasesState = _newReleasesState.asStateFlow()

    private val _trendingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingState = _trendingState.asStateFlow()

    private val _popularState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val popularState = _popularState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    // TODO: Нужно завести разные таблицы (или таблицу-словарь по типу контента

    init {
        viewModelScope.launch {
            launch {
                repository.getCacheTrendingMovies().collectToUiState(_trendingState)
            }
            launch {
                repository.getCachePopularMovies().collectToUiState(_popularState)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            async {
                repository.refreshTrendingMovies()
            }.await()
            _isRefreshing.value = false
        }
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            repository.updateFavorite(movieId)
        }
    }
}
package com.drygin.popcornplan.features.home.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.usecase.ToggleFavoriteUseCase
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val movieRepo: IMovieRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val isRefreshing = mutableStateOf(false)
    val movies = mutableStateOf<List<TrendingMovie>>(emptyList())

    suspend fun refresh(): List<TrendingMovie> {
        isRefreshing.value = true
        val newMovies = movieRepo.getTopTrending(10)
        movies.value = newMovies
        isRefreshing.value = false
        return newMovies
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
        }
    }
}
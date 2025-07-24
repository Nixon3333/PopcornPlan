package com.drygin.popcornplan.features.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.favorite.usecase.ToggleFavoriteMovieUseCase
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.common.domain.movie.usecase.MovieUseCases
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
class HomeScreenViewModel(
    private val movieUseCases: MovieUseCases,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase
) : ViewModel() {

    val isRefreshing = mutableStateOf(false)
    val movies = mutableStateOf<List<TrendingMovie>>(emptyList())

    suspend fun refresh(): List<TrendingMovie> {
        isRefreshing.value = true
        val newMovies = movieUseCases.getTopTrending(10)
        movies.value = newMovies
        isRefreshing.value = false
        return newMovies
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movieId)
        }
    }
}
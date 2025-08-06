package com.drygin.popcornplan.features.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.details.usecase.DetailsUseCases
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class DetailsScreenViewModel(
    private val detailsUseCases: DetailsUseCases,
    private val favouriteUseCases: FavouriteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                detailsUseCases.observeMovieDetails(movieId),
                favouriteUseCases.existUseCase(movieId)
            ) { movie, isFavorite ->
                movie.copy(isFavorite = isFavorite)
            }.collect {
                _movie.value = it
            }
        }

        viewModelScope.launch {
            detailsUseCases.refreshMovieDetails(movieId)
        }
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            favouriteUseCases.toggleFavorite(movieId)
        }
    }
}
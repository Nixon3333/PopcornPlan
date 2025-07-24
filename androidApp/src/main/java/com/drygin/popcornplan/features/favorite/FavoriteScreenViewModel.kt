package com.drygin.popcornplan.features.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
class FavoriteScreenViewModel(
    private val favouriteUseCases: FavouriteUseCases
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            favouriteUseCases.getFavourite().collect {
                _movies.value = it
            }
        }
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            favouriteUseCases.toggleFavorite(movieId)
        }
    }
}
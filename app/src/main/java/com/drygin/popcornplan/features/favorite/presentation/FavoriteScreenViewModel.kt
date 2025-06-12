package com.drygin.popcornplan.features.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.favorite.domain.repository.IFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    val repository: IFavoriteRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect {
                _movies.value = it
            }
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(movieId)
        }
    }
}
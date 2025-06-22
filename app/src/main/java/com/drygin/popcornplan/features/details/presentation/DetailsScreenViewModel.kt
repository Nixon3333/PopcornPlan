package com.drygin.popcornplan.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.domain.usecase.ToggleFavoriteUseCase
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: IDetailsRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeMovieDetails(movieId)
                .collect {
                    _movie.value = it
                }
        }

        viewModelScope.launch {
            repository.refreshMovieDetails(movieId)
        }
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
        }
    }
}
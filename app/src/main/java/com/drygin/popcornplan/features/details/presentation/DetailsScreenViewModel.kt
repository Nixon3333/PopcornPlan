package com.drygin.popcornplan.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import com.drygin.popcornplan.features.home.domain.model.Movie
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
        }
    }
}
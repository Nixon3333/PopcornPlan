package com.drygin.popcornplan.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    private val _movies = repository.getTrendingMovies().cachedIn(viewModelScope)
    val movies: Flow<PagingData<TrendingMovie>> = _movies

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            repository.onToggleFavorite(movieId)
        }
    }
}
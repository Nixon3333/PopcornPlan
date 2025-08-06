package com.drygin.popcornplan.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.common.domain.search.usecase.SearchUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchScreenViewModel(
    private val searchUseCases: SearchUseCases,
    private val favouriteUseCases: FavouriteUseCases
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _favorites = MutableStateFlow<List<Int>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            favouriteUseCases.getFavourite().collect { favorites ->
                _favorites.value = favorites.map { it.ids.trakt }
            }
        }
        viewModelScope.launch {
            _query
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(emptyList())
                    } else {
                        flow {
                            _isLoading.value = true
                            try {
                                val ids = searchUseCases.searchMovies(query)
                                emitAll(
                                    searchUseCases.observeMoviesByIds(ids)
                                )
                            } catch (e: Exception) {
                                _errorMessage.value = e.stackTraceToString()
                                _isLoading.value = false
                                emit(emptyList())
                            }
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect { movies ->
                    _movies.value = movies
                    _errorMessage.value = null
                    _isLoading.value = false
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            favouriteUseCases.toggleFavorite(movieId)
        }
    }
}
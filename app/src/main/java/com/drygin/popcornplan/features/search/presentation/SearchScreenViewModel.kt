package com.drygin.popcornplan.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.domain.usecase.ToggleFavoriteUseCase
import com.drygin.popcornplan.features.search.domain.repository.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchRepo: ISearchRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            _query
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(emptyList())
                    } else {
                        flow {
                            try {
                                val ids = searchRepo.searchAndStoreMovies(query)
                                emitAll(
                                    searchRepo.observeMoviesByIds(ids)
                                )
                            } catch (e: Exception) {
                                _errorMessage.value = e.stackTraceToString()
                                emit(emptyList())
                            }
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect { movies ->
                    _movies.value = movies
                    _errorMessage.value = null
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onToggleFavorite(movieId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
        }
    }
}
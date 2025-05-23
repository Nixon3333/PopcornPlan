package com.drygin.popcornplan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.domain.model.Movie
import com.drygin.popcornplan.ui.screens.sampleMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _newReleases = MutableStateFlow<List<Movie>>(emptyList())
    val newReleases = _newReleases.asStateFlow()

    private val _trending = MutableStateFlow<List<Movie>>(emptyList())
    val trending = _trending.asStateFlow()

    private val _recommendations = MutableStateFlow<List<Movie>>(emptyList())
    val recommendations = _recommendations.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Вызов API
            delay(1000) // Имитация
            _trending.value = sampleMovies().shuffled().take(5)
            _newReleases.value = sampleMovies().shuffled().take(5)
            _recommendations.value = sampleMovies().shuffled().take(5)
            _isRefreshing.value = false
        }
    }
}
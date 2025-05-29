package com.drygin.popcornplan.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.utils.collectToUiState
import com.drygin.popcornplan.features.search.domain.model.SearchItem
import com.drygin.popcornplan.features.search.domain.repository.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: ISearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<SearchItem>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun onQueryChanged(newQuery: String) {
        viewModelScope.launch {
            delay(300)
            _query.value = newQuery
            repository.searchMovie(newQuery).collectToUiState(_uiState)
        }
    }
}
package com.drygin.popcornplan.features.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.ui.components.MovieList

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = query,
            onValueChange = viewModel::onQueryChanged,
            placeholder = { Text("Search for movies...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UiState.Success -> {
                MovieList(state.data.map { it.movie }, onMovieClick)
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("No results", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

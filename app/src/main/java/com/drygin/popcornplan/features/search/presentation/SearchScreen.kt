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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.ui.components.MovieList
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.features.search.domain.model.SearchItem
import com.drygin.popcornplan.preview.PreviewMocks

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Composable
fun SearchScreenContainer(
    viewModel: SearchScreenViewModel,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val onQueryChanged = viewModel::onQueryChanged

    SearchScreen(
        uiState,
        query,
        onQueryChanged = onQueryChanged,
        onMovieClick = onMovieClick,
        onToggleFavorite = onToggleFavorite
    )
}

@Composable
fun SearchScreen(
    uiState: UiState<List<SearchItem>>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
        .padding(16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            placeholder = { Text("Search for movies...") },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
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
                MovieList(state.data.map { it.movie }, onMovieClick, onToggleFavorite)
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
@Preview
@Composable
fun SearchScreenPreview() {
    PopcornPlanTheme(darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen(
                UiState.Success(PreviewMocks.sampleSearchItems),
                "",
                {},
                {},
                {}
            )
        }
    }
}

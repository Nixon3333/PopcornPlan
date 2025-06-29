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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.components.MovieList
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.preview.PreviewMocks

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Composable
fun SearchScreenContainer(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movies by viewModel.movies.collectAsState()
    val query by viewModel.query.collectAsState()
    val onQueryChanged = viewModel::onQueryChanged

    SearchScreen(
        movies,
        query,
        onQueryChanged = onQueryChanged,
        onMovieClick = onMovieClick,
        onToggleFavorite = viewModel::onToggleFavorite
    )
}

@Composable
fun SearchScreen(
    movies: List<Movie>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFieldValueState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = query, selection = TextRange(query.length)))
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.PaddingMedium)
    ) {
        TextField(
            value = textFieldValueState.value,
            onValueChange = { newValue ->
                textFieldValueState.value = newValue
                onQueryChanged(newValue.text)
            },
            placeholder = { Text("Search for movies...") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            movies.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("No results", modifier = Modifier.align(Alignment.Center))
                }
            }

            else -> {
                MovieList(movies, onMovieClick, onToggleFavorite)
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
                PreviewMocks.sampleMovies,
                "",
                {},
                {},
                {}
            )
        }
    }
}

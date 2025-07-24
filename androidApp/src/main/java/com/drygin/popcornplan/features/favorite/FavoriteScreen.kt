package com.drygin.popcornplan.features.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.core.ui.components.MovieList
import com.drygin.popcornplan.core.ui.theme.Dimens
import com.drygin.popcornplan.core.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.preview.PreviewMocks
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
@Composable
fun FavoriteScreenContainer(
    viewModel: FavoriteScreenViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movies by viewModel.movies.collectAsState()

    FavoriteScreen(
        movies,
        onMovieClick,
        viewModel::onToggleFavorite
    )
}

@Composable
fun FavoriteScreen(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = Dimens.PaddingSmall)
    ) {
        if (movies.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Нет избранных фильмов", color = Color.Gray)
            }
        } else {
            MovieList(movies, onMovieClick, onToggleFavorite)
        }
    }
}

@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
fun FavoritePreview() {
    PopcornPlanTheme(darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen(PreviewMocks.sampleTrendingMovies.map { it.movie }, {}) { }
        }
    }
}
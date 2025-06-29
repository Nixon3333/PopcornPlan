package com.drygin.popcornplan.features.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
@Composable
fun DetailsScreenContainer(
    viewModel: DetailsScreenViewModel,
    onBack: () -> Unit
) {
    val movie by viewModel.movie.collectAsState()

    DetailsScreen(
        movie,
        viewModel::onToggleFavorite,
        onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movie: Movie?,
    onToggleFavorite: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = movie?.title ?: "Загрузка...",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, "Назад")
                }
            }
        )

        movie?.let {
            MovieDetailsContent(it, onToggleFavorite)
        }

        if (movie?.overview?.isEmpty() == true)
            EmptyMovieOverview()
    }
}

@Composable
fun EmptyMovieOverview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Описание не загружено",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun DetailsScreenPreview() {
    PopcornPlanTheme(darkTheme = true) {
        DetailsScreen(Movie(), {}) {}
    }
}
package com.drygin.popcornplan.features.favorite.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drygin.popcornplan.features.home.presentation.MovieCard

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movie by viewModel.movie.collectAsState()

    if (movie.isEmpty()) {
        // Пустой экран
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Нет избранных фильмов", color = Color.Gray)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movie) {movieItem ->
                MovieCard(
                    movie = movieItem,
                    onToggleFavorite = { viewModel.removeFavorite(movieItem.ids.trakt) },
                    onClick = { onMovieClick(movieItem.ids.trakt) }
                )
            }
        }
    }
}
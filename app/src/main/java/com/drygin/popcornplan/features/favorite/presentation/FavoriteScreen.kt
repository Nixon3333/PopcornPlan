package com.drygin.popcornplan.features.favorite.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.drygin.popcornplan.common.ui.components.MovieList

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movies by viewModel.movies.collectAsState()

    if (movies.isEmpty()) {
        // Пустой экран
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Нет избранных фильмов", color = Color.Gray)
        }
    } else {
        MovieList(movies, onMovieClick)
    }
}
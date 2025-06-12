package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.drygin.popcornplan.common.domain.model.Movie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn {
        items(movies, key = { it.ids.trakt }) { movie ->
            MovieItem(movie) {
                onMovieClick(movie.ids.trakt)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onMovieClick() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = "https://" + movie.images.poster.firstOrNull(),
                contentDescription = movie.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(movie.title ?: "No Title", style = MaterialTheme.typography.bodyMedium)
                Text("Year: ${movie.year ?: "-"}")
            }
        }
    }
}
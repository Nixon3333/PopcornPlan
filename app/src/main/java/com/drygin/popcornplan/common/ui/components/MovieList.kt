package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.common.ui.theme.Dimens

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = Dimens.PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingSmall)
    ) {
        items(movies, key = { it.ids.trakt }) { movie ->
            val movieId = movie.ids.trakt
            val clickHandler = remember(movieId) { { onMovieClick(movieId) } }
            val toggleHandler = remember(movieId) { { onToggleFavorite(movieId) } }

            MovieItem(
                movie,
                onMovieClick = clickHandler,
                onToggleFavorite = toggleHandler
            )
        }
    }
}


@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(Dimens.MovieCardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PosterImage(
                model = "https://${movie.images.poster.firstOrNull()}",
                movie.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Year: ${movie.year}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            FavoriteButton(
                movie.isFavorite,
                modifier = Modifier
                    .padding(Dimens.FavoriteButtonPadding)
                    .size(Dimens.FavoriteButtonSize)
            ) {
                onToggleFavorite()
            }
        }
    }
}
package com.drygin.popcornplan.common.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.domain.model.Movie
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
    var visibleItems = remember { mutableListOf<Movie>() }

    LaunchedEffect(movies) {
        visibleItems.clear()
        visibleItems.addAll(movies)
    }

    LazyColumn {
        items(movies, key = { it.ids.trakt }) { movie ->
            var visible by remember { mutableStateOf(true) }

            AnimatedVisibility(
                visible = visible,
                exit = fadeOut() + shrinkVertically(),
                enter = fadeIn() + expandVertically()
            ) {
                MovieItem(
                    movie,
                    onMovieClick = { onMovieClick(movie.ids.trakt) },
                    onToggleFavorite = {
                        onToggleFavorite(movie.ids.trakt)
                    }
                )
            }
        }
    }
}


@Composable
fun MovieItem(
    movie: Movie, onMovieClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingSmall)
            .clickable { onMovieClick() }
            .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius)),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(Dimens.MovieCardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShimmerAsyncImage(
                    model = "https://${movie.images.poster.firstOrNull()}",
                    movie.title,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius))
                )
                    Spacer(modifier = Modifier.width(12.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = Dimens.PaddingSmall),
                        shape = RoundedCornerShape(Dimens.MovieCardCornerRadius),
                        tonalElevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.PaddingSmall)
                        ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(end = Dimens.FavoriteButtonSize + Dimens.FavoriteButtonPadding * 2)
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
                                .align(Alignment.CenterEnd)
                                .size(Dimens.FavoriteButtonSize)
                        ) {
                            onToggleFavorite()
                        }
                    }
                }
            }
    }
}
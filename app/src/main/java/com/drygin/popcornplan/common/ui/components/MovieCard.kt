package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.theme.Dimens

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun MovieCard(
    movie: Movie,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    val posterUrl = remember(movie.ids.trakt) {
        "https://" + movie.images.poster.firstOrNull().orEmpty()
    }

    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(Dimens.MovieCardCornerRadius),
        onClick = onClick,
        modifier = Modifier.width(Dimens.MovieCardWidth)
    ) {
        Column(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .height(Dimens.MovieCardHeight)
                    .fillMaxWidth()
            ) {
                ShimmerAsyncImage(
                    model = posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .height(Dimens.MovieCardHeight)
                        .fillMaxWidth()
                )
                FavoriteButton(
                    movie.isFavorite,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(Dimens.FavoriteButtonSize)
                ) {
                    onToggleFavorite()
                }
            }

            Spacer(modifier = Modifier.height(Dimens.VerticalItemSpacing))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = movie.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.year.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.drygin.popcornplan.R
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
    val context = LocalContext.current
    val posterUrl = remember(movie.images.poster) {
        "https://" + movie.images.poster.firstOrNull().orEmpty()
    }

    val imageRequest = remember(posterUrl) {
        ImageRequest.Builder(context)
            .data(posterUrl)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    val painter = rememberAsyncImagePainter(imageRequest)
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(Dimens.MovieCardCornerRadius),
        onClick = onClick,
        modifier = Modifier.width(Dimens.MovieCardWidth)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Box(
                modifier = Modifier
                    .height(Dimens.MovieCardHeight)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .height(Dimens.MovieCardHeight)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimens.MovieCardCornerRadius,
                                topEnd = Dimens.MovieCardCornerRadius
                            )
                        ),
                    contentScale = ContentScale.Crop
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
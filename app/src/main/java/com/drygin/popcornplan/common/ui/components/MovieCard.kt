package com.drygin.popcornplan.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    Column(
        modifier = Modifier
            .width(Dimens.MovieCardWidth)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(Dimens.MovieCardHeight)
                .width(Dimens.MovieCardHeight)
        ) {
            Image(
                painter = painter,
                contentDescription = movie.title,
                modifier = Modifier
                    .height(Dimens.MovieCardHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius)),
                contentScale = ContentScale.Crop
            )

            // Кнопка избранного
            IconButton(
                onClick = { onToggleFavorite() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (movie.isFavorite) "Убрать из избранного" else "Добавить в избранное",
                    tint = if (movie.isFavorite) Color.Red else Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.VerticalItemSpacing))

        Text(
            text = movie.title,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = movie.year.toString(),
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
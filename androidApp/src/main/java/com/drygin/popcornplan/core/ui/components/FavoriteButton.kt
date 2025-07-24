package com.drygin.popcornplan.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Drygin Nikita on 13.06.2025.
 */
@Composable
fun FavoriteButton(
    movieFavoriteState: Boolean,
    modifier: Modifier = Modifier,
    onToggleFavorite: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        IconButton(
            onClick = { onToggleFavorite() },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxSize()
        ) {
            Icon(
                imageVector = if (movieFavoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (movieFavoriteState) "Убрать из избранного" else "Добавить в избранное",
                tint = if (movieFavoriteState) Color.Red else Color.White
            )
        }
    }
}
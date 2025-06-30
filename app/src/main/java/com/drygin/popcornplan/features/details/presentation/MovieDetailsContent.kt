package com.drygin.popcornplan.features.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.components.FavoriteButton
import com.drygin.popcornplan.common.ui.components.PosterImage
import com.drygin.popcornplan.common.ui.theme.Dimens

/**
 * Created by Drygin Nikita on 18.06.2025.
 */
@Composable
fun MovieDetailsContent(
    movie: Movie,
    onToggleFavorite: (Int) -> Unit
) {
    Box {
        PosterImage(
            model = "https://" + movie.images.fanart.firstOrNull(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .blur(20.dp)
        )

        Column(
            modifier = Modifier
                .padding(top = 200.dp, start = Dimens.PaddingSmall, end = Dimens.PaddingSmall)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                PosterImage(
                    model = "https://" + movie.images.poster.firstOrNull(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Dimens.PaddingSmall)
                        
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium.addStroke(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Year: ${movie.year}",
                        style = MaterialTheme.typography.bodyLarge.addStroke(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                FavoriteButton(
                    movie.isFavorite,
                    modifier = Modifier
                        .padding(end = Dimens.PaddingSmall)
                        .size(Dimens.FavoriteButtonSize)
                ) {
                    onToggleFavorite(movie.ids.trakt)
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(movie.overview, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun TextStyle.addStroke() =
    this.copy(
        shadow = Shadow(
            color = Color.Black,
            offset = Offset(1f, 1f),
            blurRadius = 1f
        )
    )

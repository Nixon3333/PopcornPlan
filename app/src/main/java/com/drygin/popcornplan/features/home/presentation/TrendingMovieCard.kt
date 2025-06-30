package com.drygin.popcornplan.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.drygin.popcornplan.common.ui.components.MovieCard
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun TrendingCard(
    trendingMovie: TrendingMovie,
    onClick: () -> Unit
) {
    Box {
        MovieCard(
            trendingMovie.movie,
        ) { onClick() }

        // Счётчик просмотров — накладывается сверху MovieCard
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.PaddingSmall)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(Dimens.MovieCardCornerRadius)
                )
                .padding(horizontal = Dimens.PaddingSmall, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "Watching count",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = trendingMovie.watchers.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
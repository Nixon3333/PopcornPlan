package com.drygin.popcornplan.preview.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import com.drygin.popcornplan.features.home.presentation.TrendingCard

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun HorizontalMovieListPreview(
    movies: List<TrendingMovie>,
    listState: LazyListState,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalItemSpacing)
    ) {
        items(
            items = movies,
            key = { movie -> movie.movie.ids.trakt }
        ) { movie ->
            TrendingCard(
                movie,
                onClick = { onMovieClick(movie.movie.ids.trakt) },
                onToggleFavorite = { onToggleFavorite(movie.movie.ids.trakt) }
            )
        }
    }
}
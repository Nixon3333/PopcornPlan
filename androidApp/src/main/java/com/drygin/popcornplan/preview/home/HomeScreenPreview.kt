package com.drygin.popcornplan.preview.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.features.trending.SectionTitle

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
@Composable
fun HomeScreenPreview(
    movies: List<TrendingMovie>,
    onMovieClick: (Int) -> Unit
) {
    val horizontalListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SectionTitle(
                title = stringResource(id = R.string.trending),
                onShowAllClick = { /* TODO */ }
            )
        }
        item {
            HorizontalMovieListPreview(
                movies = movies,
                horizontalListState,
                onMovieClick = onMovieClick
            )
        }
    }
}
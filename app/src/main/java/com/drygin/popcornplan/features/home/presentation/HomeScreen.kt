package com.drygin.popcornplan.features.home.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.ui.theme.BackgroundColor
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie


/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@Composable
fun HomeScreenContainer(
    viewModel: HomeScreenViewModel,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    HomeScreen(
        movies,
        onMovieClick,
        onToggleFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    movies: LazyPagingItems<TrendingMovie>,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val recompositionCounter = remember { mutableIntStateOf(0) }
    SideEffect {
        recompositionCounter.intValue++
        Log.d("RECOMPOSE", "HomeScreen recomposed ${recompositionCounter.intValue} times")
    }

    val isRefreshing = movies.loadState.refresh == LoadState.Loading

    val horizontalListState = rememberLazyListState()

    LaunchedEffect(movies.loadState.refresh) {
        val refreshState = movies.loadState.refresh
        if (refreshState is LoadState.NotLoading) {
            horizontalListState.animateScrollToItem(0)
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { movies.refresh() },
        modifier = Modifier
            .background(BackgroundColor)
            .padding(vertical = Dimens.VerticalListSpacing)
    ) {
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
                HorizontalPagingMovieList(
                    movies = movies,
                    horizontalListState,
                    onMovieClick = onMovieClick,
                    onToggleFavorite = onToggleFavorite
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, onShowAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingMedium, vertical = Dimens.PaddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        TextButton(onClick = onShowAllClick) {
            Text(text = stringResource(R.string.show_all), color = Color.Cyan)
        }
    }
}

@Composable
fun HorizontalPagingMovieList(
    movies: LazyPagingItems<TrendingMovie>,
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
            movies.itemCount,
            key = { index -> movies.peek(index)?.movie?.ids?.trakt ?: index }) { index ->
            movies[index]?.let { movie ->
                TrendingCard(
                    movie,
                    onClick = { onMovieClick(movie.movie.ids.trakt) },
                    onToggleFavorite = { onToggleFavorite(movie.movie.ids.trakt) }
                )
            }
        }
    }
}

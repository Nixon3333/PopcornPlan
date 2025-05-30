package com.drygin.popcornplan.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.ui.theme.BackgroundColor
import com.drygin.popcornplan.common.ui.theme.Dimens


/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val newReleasesState by viewModel.newReleasesState.collectAsState()
    val trendingState by viewModel.trendingState.collectAsState()
    val recommendationsState by viewModel.recommendationsState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    HomeScreen(
        trendingState = trendingState,
        recommendationsState = recommendationsState,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.fetchNewMovies() },
        onMovieClick = onMovieClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    trendingState: UiState<List<Movie>>,
    recommendationsState: UiState<List<Movie>>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = { onRefresh() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(vertical = Dimens.VerticalListSpacing)
        ) {
            item {
                SectionTitle(
                    title = stringResource(R.string.trending),
                    onShowAllClick = { /* TODO: */ })
                when (trendingState) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp), // примерный размер списка
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Success -> {
                        HorizontalMovieList(movieIds = trendingState.data, onMovieClick = onMovieClick)
                    }
                    is UiState.Error -> {
                        // Показываем ошибку и кнопку "Повторить"
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Ошибка загрузки: ${trendingState.message}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = onRefresh) {
                                Text("Попробовать снова")
                            }
                        }
                    }
                }
            }
            item {
                SectionTitle(
                    title = stringResource(R.string.recommendations),
                    onShowAllClick = { /* TODO: */ })
                when (recommendationsState) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.MovieCardHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Success -> {
                        HorizontalMovieList(movieIds = recommendationsState.data, onMovieClick = onMovieClick)
                    }
                    is UiState.Error -> {
                        // Показываем ошибку и кнопку "Повторить"
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Ошибка загрузки: ${recommendationsState.message}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = onRefresh) {
                                Text("Попробовать снова")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    contentAlignment: Alignment = Alignment.TopStart,
    indicator: @Composable BoxScope.() -> Unit = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = state
        )
    },
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier.pullToRefresh(state = state, isRefreshing = isRefreshing, onRefresh = onRefresh),
        contentAlignment = contentAlignment
    ) {
        content()
        indicator()
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
fun HorizontalMovieList(movieIds: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalItemSpacing)
    ) {
        items(movieIds) { movie ->
            MovieCard(movie = movie, onClick = { onMovieClick(movie.ids.trakt ?: 0) })
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(Dimens.MovieCardWidth)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = "https://" + movie.images.poster.firstOrNull(),
            contentDescription = movie.title,
            modifier = Modifier
                .height(Dimens.MovieCardHeight)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimens.VerticalItemSpacing))
        Text(
            text = movie.title ?: "",
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

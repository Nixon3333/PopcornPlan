package com.drygin.popcornplan.features.home.presentation

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.model.Images
import com.drygin.popcornplan.common.domain.model.Movie
//import com.drygin.popcornplan.common.domain.model.MovieUI
import com.drygin.popcornplan.common.ui.theme.BackgroundColor
import com.drygin.popcornplan.common.ui.theme.Dimens


/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val recompositionCounter = remember { mutableIntStateOf(0) }
    SideEffect {
        recompositionCounter.intValue++
        Log.d("RECOMPOSE", "HomeScreen recomposed ${recompositionCounter.intValue} times")
    }

    val movies = viewModel.movies.collectAsLazyPagingItems()
    val isRefreshing = movies.loadState.refresh == LoadState.Loading

    val horizontalListState = rememberLazyListState()

    // Скроллим вверх, когда refresh завершается
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
fun ErrorItem(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ошибка загрузки: ${error.localizedMessage}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Попробовать снова")
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
fun HorizontalPagingMovieList(
    movies: LazyPagingItems<Movie>,
    listState: LazyListState,
    onMovieClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalItemSpacing)
    ) {
        items(movies.itemCount, key = { index -> movies.peek(index)?.ids?.trakt ?: index }) { index ->
            movies[index]?.let { movie ->
                MovieCard(
                    movie,
                    /*movie.ids.trakt,
                    movie.title,
                    movie.images,
                    movie.isFavorite,
                    movie.watchers,
                    movie.year,*/
                    onClick = { onMovieClick(movie.ids.trakt) },
                    onToggleFavorite = { onToggleFavorite(movie.ids.trakt) }
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    /*id: Int,
    title: String,
    images: Images,
    isFavorite: Boolean,
    watchers: Int,
    year: Int,*/
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
            /*AsyncImage(
                model = posterUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(Dimens.MovieCardHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius))
            )*/
            /*SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = title,
                loading = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray)
                    )
                },
                error = {
                    Icon(Icons.Default.Clear, contentDescription = null, tint = Color.Red)
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(Dimens.MovieCardHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius))
            )*/
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

            // Счётчик просмотров
            if (movie.watchers != 0) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = "Watching count",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.watchers.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
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

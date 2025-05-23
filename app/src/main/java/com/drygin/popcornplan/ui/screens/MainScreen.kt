package com.drygin.popcornplan.ui.screens

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
import coil.compose.AsyncImage
import com.drygin.popcornplan.R
import com.drygin.popcornplan.domain.model.Movie
import com.drygin.popcornplan.ui.theme.BackgroundColor
import com.drygin.popcornplan.ui.theme.Dimens
import com.drygin.popcornplan.ui.viewmodel.MainScreenViewModel


/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onMovieClick: (Int) -> Unit
) {
    val newReleases by viewModel.newReleases.collectAsState()
    val trending by viewModel.trending.collectAsState()
    val recommendations by viewModel.recommendations.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    MainScreen(
        newReleases = newReleases,
        trending = trending,
        recommendations = recommendations,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() },
        onMovieClick = onMovieClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    newReleases: List<Movie>,
    trending: List<Movie>,
    recommendations: List<Movie>,
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
                    title = stringResource(R.string.new_releases),
                    onShowAllClick = { /* TODO: */ })
                HorizontalMovieList(movies = newReleases, onMovieClick = onMovieClick)
            }
            item {
                SectionTitle(
                    title = stringResource(R.string.trending),
                    onShowAllClick = { /* TODO: */ })
                HorizontalMovieList(movies = trending, onMovieClick = onMovieClick)
            }
            item {
                SectionTitle(
                    title = stringResource(R.string.recommendations),
                    onShowAllClick = { /* TODO: */ })
                HorizontalMovieList(movies = recommendations, onMovieClick = onMovieClick)
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
fun HorizontalMovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalItemSpacing)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
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
            model = movie.posterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .height(Dimens.MovieCardHeight)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.MovieCardCornerRadius)),
            contentScale = ContentScale.Crop
        )
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

fun sampleMovies() = listOf(
    Movie(1, "Inception", 2007, "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"),
    Movie(
        2,
        "Interstellar",
        2007,
        "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
    ),
    Movie(3, "The Matrix", 2007, "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"),
    Movie(
        4,
        "The Dark Knight",
        2007,
        "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
    ),
    Movie(5, "Fight Club", 2007, "https://image.tmdb.org/t/p/w500/a26cQPRhJPX6GbWfQbvZdrrp9j9.jpg")
)
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
import com.drygin.popcornplan.common.ui.UiState
import com.drygin.popcornplan.common.ui.theme.BackgroundColor
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.features.home.domain.model.Movie


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
        newReleasesState = newReleasesState,
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
    newReleasesState: UiState<List<Movie>>,
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
                    title = stringResource(R.string.new_releases),
                    onShowAllClick = { /* TODO: */ }
                )
                when (newReleasesState) {
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
                        HorizontalMovieList(movies = newReleasesState.data, onMovieClick = onMovieClick)
                    }
                    is UiState.Error -> {
                        // Показываем ошибку и кнопку "Повторить"
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Ошибка загрузки: ${newReleasesState.message}")
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
                        HorizontalMovieList(movies = trendingState.data, onMovieClick = onMovieClick)
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
                        HorizontalMovieList(movies = recommendationsState.data, onMovieClick = onMovieClick)
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
    Movie(
        id = 11,
        title = "The Shawshank Redemption",
        description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
        year = 1994,
        posterUrl = "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"
    ),
    Movie(
        id = 12,
        title = "The Dark Knight",
        description = "When the menace known as the Joker emerges, he plunges Gotham into anarchy and forces Batman closer to crossing the line between hero and vigilante.",
        year = 2008,
        posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
    ),
    Movie(
        id = 13,
        title = "Inception",
        description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea.",
        year = 2010,
        posterUrl = "https://image.tmdb.org/t/p/w500/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg"
    ),
    Movie(
        id = 14,
        title = "Interstellar",
        description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
        year = 2014,
        posterUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg"
    ),
    Movie(
        id = 15,
        title = "Forrest Gump",
        description = "The presidencies of Kennedy and Johnson, the Vietnam War, Watergate, and other history unfold through the perspective of an Alabama man.",
        year = 1994,
        posterUrl = "https://image.tmdb.org/t/p/w500/saHP97rTPS5eLmrLQEcANmKrsFl.jpg"
    ),
    Movie(
        id = 16,
        title = "The Matrix",
        description = "A computer hacker learns about the true nature of his reality and his role in the war against its controllers.",
        year = 1999,
        posterUrl = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"
    ),
    Movie(
        id = 17,
        title = "Fight Club",
        description = "An insomniac office worker forms an underground fight club that evolves into something much more.",
        year = 1999,
        posterUrl = "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
    ),
    Movie(
        id = 18,
        title = "Pulp Fiction",
        description = "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine in tales of violence and redemption.",
        year = 1994,
        posterUrl = "https://image.tmdb.org/t/p/w500/dM2w364MScsjFf8pfMbaWUcWrR.jpg"
    ),
    Movie(
        id = 19,
        title = "The Lord of the Rings: The Fellowship of the Ring",
        description = "A meek Hobbit and eight companions set out on a journey to destroy the One Ring.",
        year = 2001,
        posterUrl = "https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg"
    ),
    Movie(
        id = 20,
        title = "The Avengers",
        description = "Earth's mightiest heroes must come together and learn to fight as a team to stop Loki.",
        year = 2012,
        posterUrl = "https://image.tmdb.org/t/p/w500/RYMX2wcKCBAr24UyPD7xwmjaTn.jpg"
    )
)

package com.drygin.popcornplan.features.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.core.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel


/**
 * Created by Drygin Nikita on 22.05.2025.
 */
@Composable
fun HomeScreenContainer(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movies = viewModel.movies.value
    val isRefreshing = viewModel.isRefreshing.value
    val context = LocalContext.current

    val isFirstLoad = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (isFirstLoad.value) {
            val loaded = viewModel.refresh()
            preloadPosters(context, loaded)
            isFirstLoad.value = false
        }
    }

    val coroutineScope = rememberCoroutineScope()

    HomeScreen(
        movies,
        isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                val newMovies = viewModel.refresh()
                preloadPosters(context, newMovies)
            }
        },
        onMovieClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    movies: List<TrendingMovie>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val recompositionCounter = remember { mutableIntStateOf(0) }
    SideEffect {
        recompositionCounter.intValue++
        Log.d("RECOMPOSE", "HomeScreen recomposed ${recompositionCounter.intValue} times")
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh() },
        modifier = Modifier
            .padding(vertical = Dimens.VerticalListSpacing)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            SectionTitle(
                title = stringResource(id = R.string.trending),
                onShowAllClick = { /* TODO */ },
            )
            HorizontalPagingMovieList(
                movies = movies,
                onMovieClick = onMovieClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.MovieCardHeight + 72.dp + 24.dp)
            )
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
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        TextButton(onClick = onShowAllClick) {
            Text(text = stringResource(R.string.show_all))
        }
    }
}

@Composable
fun HorizontalPagingMovieList(
    movies: List<TrendingMovie>,
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.HorizontalItemSpacing),
        modifier = modifier
    ) {
        items(
            count = movies.size,
            key = { index -> movies[index].movie.ids.trakt },
            contentType = { "movie_card" }
        ) { index ->
            println("Отрисовываем карточку $index")
            val movie = movies[index]
                TrendingCard(
                    movie,
                    onClick = { onMovieClick(movie.movie.ids.trakt) },
                )
        }
    }
}

suspend fun preloadPosters(context: Context, movies: List<TrendingMovie>) {
    withContext(Dispatchers.IO) {
        val imageLoader = Coil.imageLoader(context)

        Log.d("PRELOAD", "▶️ Начинаем прогрев ${movies.size} постеров")

        val start = System.currentTimeMillis()

        movies.forEach { movie ->
            val posterUrl = "https://${movie.movie.images.poster.firstOrNull()}"
            val request = ImageRequest.Builder(context)
                .data(posterUrl)
                .size(128, 192)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()

            val result = imageLoader.execute(request)
            if (result is SuccessResult) {
                Log.d("PRELOAD", "✅ ${movie.movie.title} -> ${result.dataSource}")
            } else {
                Log.e("PRELOAD", "❌ Ошибка загрузки ${movie.movie.title}")
            }
        }

        val duration = System.currentTimeMillis() - start
        Log.d("PRELOAD", "🏁 Прогрев завершён за ${duration}ms")
    }
}

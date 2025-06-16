package com.drygin.popcornplan.features.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.common.ui.components.FavoriteButton
import com.drygin.popcornplan.common.ui.theme.Dimens
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
@Composable
fun DetailsScreenContainer(
    viewModel: DetailsScreenViewModel
) {
    val movie by viewModel.movie.collectAsState()
    DetailsScreen(movie)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movie: Movie?
) {
    movie?.let {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            AsyncImage(
                model = "https://" + movie.images.fanart.firstOrNull(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .blur(20.dp)
            )

            Column(
                modifier = Modifier
                    .padding(top = 200.dp, start = 8.dp, end = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = "https://" + movie.images.poster.firstOrNull(),
                        contentDescription = movie.title,
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(movie.title, style = MaterialTheme.typography.titleLarge)
                        Text("Year: ${movie.year}")
                    }
                    FavoriteButton(
                        movie.isFavorite,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(Dimens.FavoriteButtonSize)
                    ) { }
                }

                Spacer(Modifier.height(16.dp))
                Text(movie.overview, style = MaterialTheme.typography.bodyMedium)
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(
    showBackground = true
)
@Composable
fun DetailsScreenPreview() {
    PopcornPlanTheme(darkTheme = true) {
        DetailsScreen(Movie())
    }
}
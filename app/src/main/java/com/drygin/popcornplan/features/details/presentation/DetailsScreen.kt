package com.drygin.popcornplan.features.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.drygin.popcornplan.common.ui.theme.Dimens

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val movie by viewModel.movie.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = movie?.title ?: "Loading") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        movie?.let {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(Dimens.PaddingMedium)
            ) {
                AsyncImage(
                    model = "https://" + it.images.banner.firstOrNull(),
                    contentDescription = it.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(Dimens.VerticalListSpacing))
                Text(text = it.title ?: "Нет тайтла", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                Text("Год: ${it.year}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(Dimens.VerticalListSpacing))
                Text("Описание: ${it.overview}", style = MaterialTheme.typography.bodyLarge)
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
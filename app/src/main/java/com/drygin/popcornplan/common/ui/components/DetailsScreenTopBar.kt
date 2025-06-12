package com.drygin.popcornplan.common.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.drygin.popcornplan.features.details.presentation.DetailsScreenViewModel

/**
 * Created by Drygin Nikita on 12.06.2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTopBar(
    viewModel: DetailsScreenViewModel,
    onBackClick: () -> Unit
) {
    val movie by viewModel.movie.collectAsState()

    TopAppBar(
        title = { Text(text = movie?.title ?: "Loading") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, "Назад")
            }
        }
    )
}
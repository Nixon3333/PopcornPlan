package com.drygin.popcornplan.features.home.domain.model

import androidx.compose.runtime.Immutable
import com.drygin.popcornplan.common.domain.model.Movie

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Immutable
data class TrendingMovie(
    val watchers: Int,
    val movie: Movie,
    val page: Int
)

package com.drygin.popcornplan.common.domain.movie.model

import androidx.compose.runtime.Immutable

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Immutable
data class TrendingMovie(
    val watchers: Int,
    val movie: Movie,
    val page: Int
)
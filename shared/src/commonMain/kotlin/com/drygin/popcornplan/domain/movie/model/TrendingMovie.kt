package com.drygin.popcornplan.common.domain.movie.model

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
data class TrendingMovie(
    val watchers: Int,
    val movie: Movie,
    val page: Int
)
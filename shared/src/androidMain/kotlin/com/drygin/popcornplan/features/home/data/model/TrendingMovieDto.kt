package com.drygin.popcornplan.features.home.data.model

import com.drygin.popcornplan.data.model.MovieDto

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
data class TrendingMovieDto(
    val watchers: Int,
    val movie: MovieDto
)

package com.drygin.popcornplan.features.trending.data.remote.dto

import com.drygin.popcornplan.data.remote.dto.MovieDto
import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Serializable
data class TrendingMovieDto(
    val watchers: Int,
    val movie: MovieDto
)
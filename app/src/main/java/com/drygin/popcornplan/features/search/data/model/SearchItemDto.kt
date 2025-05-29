package com.drygin.popcornplan.features.search.data.model

import com.drygin.popcornplan.common.data.model.MovieDto

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
data class SearchItemDto(
    val type: String,
    val score: Double,
    val movie: MovieDto
)

package com.drygin.popcornplan.features.search.data.remote.dto

import com.drygin.popcornplan.data.remote.dto.MovieDto
import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@Serializable
data class SearchItemDto(
    val type: String,
    val score: Double,
    val movie: MovieDto
)
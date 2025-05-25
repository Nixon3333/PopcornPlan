package com.drygin.popcornplan.features.details.domain.model

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String?,
    val runtimeMinutes: Int?,
    val rating: Double,
    val genres: List<String>
)

package com.drygin.popcornplan.features.details.data.model

import com.squareup.moshi.Json

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val vote_average: Double,
    @Json(name = "genres") val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)

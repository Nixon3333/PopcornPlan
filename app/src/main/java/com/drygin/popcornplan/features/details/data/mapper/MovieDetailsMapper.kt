package com.drygin.popcornplan.features.details.data.mapper

import com.drygin.popcornplan.features.details.data.model.MovieDetailsDto
import com.drygin.popcornplan.features.details.domain.model.MovieDetails

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = release_date,
        runtimeMinutes = runtime,
        rating = vote_average,
        genres = genres.map { it.name }
    )
}
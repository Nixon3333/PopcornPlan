package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.features.home.data.model.MovieDto
import com.drygin.popcornplan.features.home.domain.model.Movie

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        description = description,
        year = year,
        posterUrl = posterUrl
    )
}
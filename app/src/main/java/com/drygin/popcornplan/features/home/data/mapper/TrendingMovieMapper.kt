package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.features.home.data.model.TrendingMovieDto
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
fun TrendingMovieDto.toDomain(): TrendingMovie =
    TrendingMovie(
        watchers = watchers,
        movie.toDomain()
    )
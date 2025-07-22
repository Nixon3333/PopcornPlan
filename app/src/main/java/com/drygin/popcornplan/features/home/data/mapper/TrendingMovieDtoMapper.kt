package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.mapper.dto.toDomain
import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.features.home.data.model.TrendingMovieDto
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovieDto.toDomain(page: Int = 1) = TrendingMovie(
    watchers = watchers,
    movie = movie.toDomain(),
    page = page
)

fun TrendingMovieDto.toMovieDto(): MovieDto {
    return movie.copy(watchers = watchers)
}

package com.drygin.popcornplan.reatures.trending.data.mapper

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.data.mapper.dto.toDomain
import com.drygin.popcornplan.data.remote.dto.MovieDto
import com.drygin.popcornplan.features.trending.data.remote.dto.TrendingMovieDto

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

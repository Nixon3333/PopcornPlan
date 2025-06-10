package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.data.mapper.toEntity
import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.features.home.data.model.TrendingMovieDto
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
fun TrendingMovieDto.toDomain(page: Int) =
    TrendingMovie(
        watchers = watchers,
        movie = movie.toDomain(),
        page = page
    )

fun TrendingMovieDto.toEntity(page: Int): TrendingMovieEntity {
    return TrendingMovieEntity(
        traktId = this.movie.ids.trakt,
        watchers = watchers,
        pageIndex = page
    )
}

fun TrendingMovieDto.toMovieDto(): MovieDto {
    return movie.copy(watchers = watchers)
}

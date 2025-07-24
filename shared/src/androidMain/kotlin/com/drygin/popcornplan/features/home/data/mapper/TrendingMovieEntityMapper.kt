package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.data.local.relation.TrendingMovieWithImages
import com.drygin.popcornplan.data.mapper.entity.toDomain

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovieWithImages.toDomain() = TrendingMovie(
    watchers = this.trendingMovie.watchers,
    movie = movie.toDomain(),
    page = this.trendingMovie.pageIndex
)
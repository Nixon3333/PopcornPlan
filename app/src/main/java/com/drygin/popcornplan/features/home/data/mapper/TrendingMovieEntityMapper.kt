package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.local.relation.TrendingMovieWithImages
import com.drygin.popcornplan.common.data.mapper.entity.toDomain
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovieWithImages.toDomain() = TrendingMovie(
    watchers = this.trendingMovie.watchers,
    movie = movieWithImage.toDomain(),
    page = this.trendingMovie.pageIndex
)
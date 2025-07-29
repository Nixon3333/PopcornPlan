package com.drygin.popcornplan.reatures.trending.data.mapper

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.data.mapper.entity.toDomain
import com.drygin.popcornplan.reatures.trending.data.local.relation.TrendingMovieWithImages

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovieWithImages.toDomain() = TrendingMovie(
    watchers = this.trendingMovie.watchers,
    movie = this.movie.toDomain(images),
    page = this.trendingMovie.pageIndex
)
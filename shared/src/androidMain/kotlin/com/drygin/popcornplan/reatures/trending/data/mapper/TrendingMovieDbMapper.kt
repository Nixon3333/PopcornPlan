package com.drygin.popcornplan.reatures.trending.data.mapper

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.reatures.trending.data.local.entity.TrendingMovieEntity

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovie.toEntity(page: Int = 1) = TrendingMovieEntity(
    traktId = this.movie.ids.trakt,
    watchers,
    page
)

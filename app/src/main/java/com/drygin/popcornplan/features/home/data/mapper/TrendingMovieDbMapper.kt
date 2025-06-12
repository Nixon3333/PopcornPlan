package com.drygin.popcornplan.features.home.data.mapper

import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun TrendingMovie.toEntity(page: Int) = TrendingMovieEntity(
    traktId = this.movie.ids.trakt,
    watchers,
    page
)

package com.drygin.popcornplan.domain.movie.repository

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie

/**
 * Created by Drygin Nikita on 23.07.2025.
 */
interface CommonMovieRepository {
    suspend fun getTopTrending(limit: Int): List<TrendingMovie>
}
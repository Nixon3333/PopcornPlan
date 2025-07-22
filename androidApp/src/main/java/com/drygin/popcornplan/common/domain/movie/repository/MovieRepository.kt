package com.drygin.popcornplan.common.domain.movie.repository

import androidx.paging.PagingData
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
interface MovieRepository {
    fun getTrendingMoviesPaging(): Flow<PagingData<TrendingMovie>>
    suspend fun getTopTrending(limit: Int): List<TrendingMovie>
}
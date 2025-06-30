package com.drygin.popcornplan.features.home.domain.repository

import androidx.paging.PagingData
import com.drygin.popcornplan.features.home.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
interface IMovieRepository {
    fun getTrendingMoviesPaging(): Flow<PagingData<TrendingMovie>>
    suspend fun getTopTrending(limit: Int): List<TrendingMovie>
}
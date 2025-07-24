package com.drygin.popcornplan.domain.repository

import androidx.paging.PagingData
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.domain.movie.repository.CommonMovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 23.07.2025.
 */
interface MovieRepository : CommonMovieRepository {
    //fun getTrendingMoviesPaging(): Flow<PagingData<TrendingMovie>>
}
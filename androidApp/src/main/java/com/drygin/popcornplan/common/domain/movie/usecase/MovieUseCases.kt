package com.drygin.popcornplan.common.domain.movie.usecase

import androidx.paging.PagingData
import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.common.domain.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class MovieUseCases(
    val getTrendingPaged: GetTrendingMoviesPagedUseCase,
    val getTopTrending: GetTopTrendingMoviesUseCase
)

class GetTrendingMoviesPagedUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<TrendingMovie>> {
        return repository.getTrendingMoviesPaging()
    }
}

class GetTopTrendingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(limit: Int): List<TrendingMovie> {
        return repository.getTopTrending(limit)
    }
}



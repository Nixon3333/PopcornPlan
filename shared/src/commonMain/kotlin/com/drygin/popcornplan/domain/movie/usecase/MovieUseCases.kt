package com.drygin.popcornplan.common.domain.movie.usecase

import com.drygin.popcornplan.common.domain.movie.model.TrendingMovie
import com.drygin.popcornplan.domain.movie.repository.CommonMovieRepository

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class MovieUseCases(
    val getTopTrending: GetTopTrendingMoviesUseCase
)

class GetTopTrendingMoviesUseCase(
    private val repository: CommonMovieRepository
) {
    suspend operator fun invoke(limit: Int): List<TrendingMovie> {
        return repository.getTopTrending(limit)
    }
}



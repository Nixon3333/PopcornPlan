package com.drygin.popcornplan.common.domain.search.usecase

import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.domain.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class SearchUseCases(
    val searchMovies: SearchMoviesUseCase,
    val observeMoviesByIds: ObserveMoviesByIdsUseCase
)

class SearchMoviesUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<Int> {
        return repository.searchAndStoreMovies(query)
    }
}

class ObserveMoviesByIdsUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(ids: List<Int>): Flow<List<Movie>> {
        return repository.observeMoviesByIds(ids)
    }
}


package com.drygin.popcornplan.common.domain.details.usecase

import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.common.domain.details.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class DetailsUseCases(
    val observeMovieDetails: ObserveMovieDetailsUseCase,
    val refreshMovieDetails: RefreshMovieDetailsUseCase
)

class ObserveMovieDetailsUseCase(
    private val repository: DetailsRepository
) {
    operator fun invoke(movieId: Int): Flow<Movie> = repository.observeMovieDetails(movieId)
}

class RefreshMovieDetailsUseCase(
    private val repository: DetailsRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.refreshMovieDetails(movieId)
}

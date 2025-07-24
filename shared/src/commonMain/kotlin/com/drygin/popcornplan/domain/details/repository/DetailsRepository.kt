package com.drygin.popcornplan.common.domain.details.repository

import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface DetailsRepository {
    fun observeMovieDetails(movieId: Int): Flow<Movie>
    suspend fun refreshMovieDetails(movieId: Int)
}
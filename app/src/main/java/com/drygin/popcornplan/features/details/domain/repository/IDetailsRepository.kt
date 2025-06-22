package com.drygin.popcornplan.features.details.domain.repository

import com.drygin.popcornplan.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface IDetailsRepository {
    fun observeMovieDetails(movieId: Int): Flow<Movie>
    suspend fun refreshMovieDetails(movieId: Int)
}
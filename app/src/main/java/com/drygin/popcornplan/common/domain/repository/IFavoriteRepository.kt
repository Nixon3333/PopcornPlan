package com.drygin.popcornplan.common.domain.repository

import com.drygin.popcornplan.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface IFavoriteRepository {
    suspend fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun onToggleFavorite(movieId: Int)
}
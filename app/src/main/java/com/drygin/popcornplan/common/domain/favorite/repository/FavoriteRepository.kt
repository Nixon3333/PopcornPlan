package com.drygin.popcornplan.common.domain.favorite.repository

import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface FavoriteRepository {
    suspend fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun onToggleFavorite(movieId: Int)
}
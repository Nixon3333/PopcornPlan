package com.drygin.popcornplan.features.home.domain.repository

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
interface IMovieRepository {
    suspend fun updateFavorite(movieId: Int)
}
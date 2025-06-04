package com.drygin.popcornplan.features.home.domain.repository

import com.drygin.popcornplan.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
interface IMovieRepository {
    fun getTrendingMovies(): Flow<Result<List<Movie>>>
    fun getCacheTrendingMovies(): Flow<Result<List<Movie>>>
    fun getCachePopularMovies(): Flow<Result<List<Movie>>>
    fun getPopularMovies(): Flow<Result<List<Movie>>>
    suspend fun refreshTrendingMovies()
    suspend fun updateFavorite(movieId: Int)
}
package com.drygin.popcornplan.features.home.domain.repository

import com.drygin.popcornplan.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
interface IMovieRepository {
    suspend fun getMovies(): Flow<Result<List<Movie>>>
    fun getTrendingMovies(): Flow<Result<List<Movie>>>
    fun getRecommendationMovies(): Flow<Result<List<Movie>>>
}
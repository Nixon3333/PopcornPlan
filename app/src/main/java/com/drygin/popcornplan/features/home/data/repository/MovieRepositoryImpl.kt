package com.drygin.popcornplan.features.home.data.repository

import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.mapper.toDomain
import com.drygin.popcornplan.features.home.domain.model.Movie
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import com.drygin.popcornplan.features.home.presentation.sampleMovies
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : IMovieRepository {
    override fun getNewMovies(): Flow<Result<List<Movie>>> = flow {
        delay(2000)
        emit(Result.success(sampleMovies().shuffled().take(10)))
        /*try {
            val dtoList = api.getNewMovies()
            val movies = dtoList.map { it.toDomain() }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }*/
    }

    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow {
        delay(3000)
        emit(Result.success(sampleMovies().shuffled().take(10)))
        /*try {
            val dtoList = api.getTrendingMovies()
            val movies = dtoList.map { it.toDomain() }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }*/
    }

    override fun getRecommendationMovies(): Flow<Result<List<Movie>>> = flow {
        emit(Result.success(sampleMovies().shuffled().take(10)))
        /*try {
            val dtoList = api.getRecommendationMovies()
            val movies = dtoList.map { it.toDomain() }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }*/
    }
}
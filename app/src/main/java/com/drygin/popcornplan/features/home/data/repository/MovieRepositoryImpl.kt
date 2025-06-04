package com.drygin.popcornplan.features.home.data.repository

import android.util.Log
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.entity.MovieEntity.Companion.fromDto
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.mapper.toMovieDto
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao
) : IMovieRepository {

    override fun getCacheTrendingMovies() = dao.getTrendingMovies().map { result ->
        Result.success(result.map { it.toDomain() })
    }

    override suspend fun refreshTrendingMovies() {
        val trending = api.getTrendingMovies().map { it.toMovieDto() }
        val popular = api.getPopularMovies()
        val response = trending + popular
        //dao.clearAll()
        // TODO: Искать избранные и проставлять favorite через upsert
        dao.insertAll(response.map { fromDto(it) })
    }

    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow {
        try {
            val dtoList = api.getTrendingMovies()
            val movies = dtoList.map { it.toMovieDto() }.map { it.toDomain() }
            movies.forEach { Log.d("movies::", "getTrendingMovies: $it") }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getCachePopularMovies(): Flow<Result<List<Movie>>> =
        dao.getMovies().map { result ->
            Result.success(result.map { it.toDomain() })
        }

    override fun getPopularMovies(): Flow<Result<List<Movie>>> = flow {
        try {
            val dtoList = api.getPopularMovies()
            val movies = dtoList.map { it.toDomain() }
            movies.forEach { Log.d("movies::", "getPopularMovies: $it") }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun updateFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            dao.getMovie(movieId)?.let {
                dao.insertAll(listOf(it.copy(favorite = !it.favorite)))
            }
        }
    }
}
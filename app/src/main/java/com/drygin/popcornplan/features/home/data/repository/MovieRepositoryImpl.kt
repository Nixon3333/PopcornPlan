package com.drygin.popcornplan.features.home.data.repository

//import com.drygin.popcornplan.features.home.presentation.sampleMovies
import android.util.Log
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 23.05.2025.
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : IMovieRepository {
    override suspend fun getMovies(): Flow<Result<List<Movie>>> = flow {
        /*try {
            val dtoList = api.getMoviesId(*//*startDate = "2025-05-01"*//*)
            val movies = dtoList.map { it.toDomain() }.map { it.movie }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }*/
    }

    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow {
        try {
            val dtoList = api.getTrendingMovies(/*startDate = "2025-05-01"*/)
            val movies = dtoList.map { it.movie.toDomain() }
            movies.forEach { Log.d("movies::", "getNewMovies: $it") }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getRecommendationMovies(): Flow<Result<List<Movie>>> = flow {
        //emit(Result.success(sampleMovies().shuffled().take(10)))
        /*try {
            val dtoList = api.getRecommendationMovies()
            val movies = dtoList.map { it.toDomain() }
            emit(Result.success(movies))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }*/
    }
}
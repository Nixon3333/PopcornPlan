package com.drygin.popcornplan.reatures.details.data.repository

import android.util.Log
import com.drygin.popcornplan.common.domain.details.repository.DetailsRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.data.local.dao.MovieDao
import com.drygin.popcornplan.data.mapper.entity.toDomain
import com.drygin.popcornplan.data.mapper.entity.toEntity
import com.drygin.popcornplan.features.details.data.remote.api.MovieDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
const val TAG = "DetailsRepositoryImpl"

class DetailsRepositoryImpl(
    private val api: MovieDetailsApi,
    private val movieDao: MovieDao
) : DetailsRepository {

    override fun observeMovieDetails(movieId: Int): Flow<Movie> =
        movieDao.observeMovieWithImages(movieId)
            .filterNotNull()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)

    override suspend fun refreshMovieDetails(movieId: Int) {
        withContext(Dispatchers.IO) {
            val localMovie = movieDao.movieWithImages(movieId)?.toDomain()

            if (localMovie == null || localMovie.overview.isEmpty()) {
                try {
                    val remoteMovieDto = api.getMovieDetails(movieId)
                    val movieEntity = remoteMovieDto.toEntity()
                    movieDao.insertAll(listOf(movieEntity))
                } catch (e: Exception) {
                    Log.e(TAG, "refreshMovieDetails error: ${e.stackTraceToString()}")
                }
            }
        }
    }
}
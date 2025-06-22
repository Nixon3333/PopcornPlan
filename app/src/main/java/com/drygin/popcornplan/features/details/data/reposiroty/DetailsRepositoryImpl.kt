package com.drygin.popcornplan.features.details.data.reposiroty

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.utils.saveMoviesPreservingFavorites
import com.drygin.popcornplan.common.data.mapper.entity.toDomain
import com.drygin.popcornplan.common.data.mapper.toEntity
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
class DetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi,
    private val movieDao: MovieDao
) : IDetailsRepository {

    override fun observeMovieDetails(movieId: Int): Flow<Movie> =
        movieDao.observeMovieWithImages(movieId)
            .filterNotNull()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)

    override suspend fun refreshMovieDetails(movieId: Int) {
        withContext(Dispatchers.IO) {
            val localMovie = movieDao.movieWithImages(movieId)?.toDomain()
            if (localMovie == null || localMovie.overview.isEmpty()) {
                val remoteMovieDto = api.getMovieDetails(movieId)
                val movieEntity = remoteMovieDto.toEntity()
                movieDao.saveMoviesPreservingFavorites(listOf(movieEntity))
            }
        }
    }
}
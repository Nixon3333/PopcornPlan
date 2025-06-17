package com.drygin.popcornplan.features.details.data.reposiroty

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.mapper.dto.toDomain
import com.drygin.popcornplan.common.data.mapper.entity.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
class DetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi,
    private val movieDao: MovieDao
) : IDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Movie {
        val movie = movieDao.movieWithImages(movieId)?.toDomain()
        return movie?.let {
            if (it.overview.isEmpty())
                api.getMovieDetails(movieId).toDomain()
            else
                it
        } ?: run {
            api.getMovieDetails(movieId).toDomain()
        }
    }
}
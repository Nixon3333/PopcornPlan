package com.drygin.popcornplan.features.details.data.reposiroty

import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
class DetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi
) : IDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Movie {
        return api.getMovieDetails(movieId).toDomain()
    }
}
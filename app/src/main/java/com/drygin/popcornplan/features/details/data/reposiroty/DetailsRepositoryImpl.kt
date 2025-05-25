package com.drygin.popcornplan.features.details.data.reposiroty

import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.data.mapper.toDomain
import com.drygin.popcornplan.features.details.domain.model.MovieDetails
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
class DetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi
) : IDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return api.getMovieDetails(movieId).toDomain()
    }
}
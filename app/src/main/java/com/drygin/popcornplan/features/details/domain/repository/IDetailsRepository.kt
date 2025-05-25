package com.drygin.popcornplan.features.details.domain.repository

import com.drygin.popcornplan.features.details.domain.model.MovieDetails

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface IDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}
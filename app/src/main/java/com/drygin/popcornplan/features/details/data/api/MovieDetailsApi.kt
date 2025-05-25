package com.drygin.popcornplan.features.details.data.api

import com.drygin.popcornplan.features.details.data.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface MovieDetailsApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailsDto
}
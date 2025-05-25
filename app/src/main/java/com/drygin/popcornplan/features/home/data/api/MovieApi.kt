package com.drygin.popcornplan.features.home.data.api

import com.drygin.popcornplan.features.home.data.model.MovieDto
import retrofit2.http.GET

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
interface MovieApi {
    @GET("movie/new")
    suspend fun getNewMovies(): List<MovieDto>

    @GET("movie/trending")
    suspend fun getTrendingMovies(): List<MovieDto>

    @GET("movie/recommendation")
    suspend fun getRecommendationMovies(): List<MovieDto>
}
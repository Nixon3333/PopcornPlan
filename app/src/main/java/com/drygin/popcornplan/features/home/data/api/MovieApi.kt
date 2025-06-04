package com.drygin.popcornplan.features.home.data.api

import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.features.home.data.model.TrendingMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
const val LIMIT = 10
interface MovieApi {
    /*@GET("movies/updates")
    suspend fun getMoviesId(
        //@Query("start_date") startDate: String,
        @Query("limit") limit: Int = LIMIT,
        @Query("page") page: Int = 1
    ): List<MovieResponseDto>*/

    @GET("movies/trending")
    suspend fun getTrendingMovies(
        @Query("limit") limit: Int = LIMIT,
        @Query("page") page: Int = 1,
        @Query("extended") extended: String = "images"
    ): List<TrendingMovieDto>

    @GET("movies/popular")
    suspend fun getPopularMovies(
        @Query("limit") limit: Int = LIMIT,
        @Query("page") page: Int = 1,
        @Query("extended") extended: String = "images"
    ): List<MovieDto>
}
package com.drygin.popcornplan.features.trending.data.remote.api

import com.drygin.popcornplan.data.remote.dto.MovieDto
import com.drygin.popcornplan.features.trending.data.remote.dto.TrendingMovieDto
import com.drygin.popcornplan.network.api.BaseTraktApi
import com.drygin.popcornplan.network.api.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class MovieApi(
    clientProvider: HttpClientProvider,
    traktApiKey: String
) : BaseTraktApi(clientProvider.getClient(), traktApiKey) {
    suspend fun getTrendingMovies(limit: Int = 30, page: Int = 1): List<TrendingMovieDto> {
        val response: HttpResponse = client.get("movies/trending") {
            traktHeaders()
            parameter("limit", limit)
            parameter("page", page)
            parameter("extended", "images")
        }

        if (response.status != HttpStatusCode.OK) {
            val raw = response.bodyAsText()
            println("Error body: $raw")
        }

        return response.body<List<TrendingMovieDto>>()
        /*return client.get("movies/trending") {
            traktHeaders()
            parameter("limit", limit)
            parameter("page", page)
            parameter("extended", "images")
        }.body()*/
    }

    suspend fun getPopularMovies(limit: Int = 30, page: Int = 1): List<MovieDto> {
        return client.get("movies/popular") {
            traktHeaders()
            parameter("limit", limit)
            parameter("page", page)
            parameter("extended", "images")
        }.body()
    }

    suspend fun getMovie(traktId: Int): MovieDto? {
        return client.get("movies/$traktId") {
            traktHeaders()
            parameter("extended", "full,images")
        }.body()
    }
}
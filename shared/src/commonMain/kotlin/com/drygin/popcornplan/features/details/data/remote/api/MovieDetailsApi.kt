package com.drygin.popcornplan.features.details.data.remote.api

import com.drygin.popcornplan.data.remote.dto.MovieDto
import com.drygin.popcornplan.network.api.BaseTraktApi
import com.drygin.popcornplan.network.api.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class MovieDetailsApi(
    clientProvider: HttpClientProvider,
    traktApiKey: String
) : BaseTraktApi(clientProvider.getClient(), traktApiKey) {
    suspend fun getMovieDetails(movieId: Int): MovieDto {
        return client.get("movies/$movieId") {
            traktHeaders()
            parameter("extended", "full,images")
        }.body()
    }
}
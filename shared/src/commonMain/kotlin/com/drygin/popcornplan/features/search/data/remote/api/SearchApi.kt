package com.drygin.popcornplan.features.search.data.remote.api

import com.drygin.popcornplan.features.search.data.remote.dto.SearchItemDto
import com.drygin.popcornplan.network.api.BaseTraktApi
import com.drygin.popcornplan.network.api.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class SearchApi(
    clientProvider: HttpClientProvider,
    traktApiKey: String
) : BaseTraktApi(clientProvider.getClient(), traktApiKey) {
    suspend fun search(query: String): List<SearchItemDto> {
        return client.get("search/movie") {
            traktHeaders()
            parameter("query", query)
            parameter("extended", "images")
        }.body()
    }
}
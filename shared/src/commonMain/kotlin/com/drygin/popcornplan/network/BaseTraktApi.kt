package com.drygin.popcornplan.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
abstract class BaseTraktApi(
    protected val client: HttpClient,
    private val traktApiKey: String
) {
    protected fun HttpRequestBuilder.traktHeaders() {
        headers {
            append("trakt-api-version", "2")
            append("trakt-api-key", traktApiKey)
        }
    }
}
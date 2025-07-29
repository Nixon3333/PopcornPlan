package com.drygin.popcornplan.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class TraktApiHttpClientProvider(
    private val json: Json
) : HttpClientProvider {

    override fun getClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json)
            }

            defaultRequest {
                url {
                    host = "api.trakt.tv"
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}
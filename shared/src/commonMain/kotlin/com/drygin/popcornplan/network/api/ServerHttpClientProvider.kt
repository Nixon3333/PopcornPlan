package com.drygin.popcornplan.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class ServerHttpClientProvider(
    private val json: Json
) : HttpClientProvider {

    override fun getClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json)
            }

            install(WebSockets)

            defaultRequest {
                url {
                    host = "10.0.2.2" // для Android-эмулятора
                    port = 8080
                    protocol = URLProtocol.HTTP
                }
            }
        }
    }
}
package com.drygin.popcornplan.data.remote

import com.drygin.popcornplan.features.auth.domain.provider.TokenProvider
import com.drygin.popcornplan.features.sync.domain.SyncEvent
import com.drygin.popcornplan.network.HttpClientProvider
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.headers
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class SyncWebSocketClient(
    private val clientProvider: HttpClientProvider,
    private val json: Json,
    private val tokenProvider: TokenProvider,
    private val url: String = "ws://10.0.2.2:8080/sync/ws",
) {
    suspend fun connect(onEvent: suspend (SyncEvent) -> Unit) {
        try {
            val token = tokenProvider.getJwtToken()
            val client = clientProvider.getClient()

            client.webSocket(
                urlString = url,
                request = {
                    headers {
                        append("Authorization", "Bearer $token")
                    }
                }
            ) {
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val event = json.decodeFromString<SyncEvent>(frame.readText())
                            onEvent(event)
                        }

                        else -> {}
                    }
                }
            }
        } catch (e: Exception) {
            println("WebSocket error: ${e.message}")
        }
    }
}
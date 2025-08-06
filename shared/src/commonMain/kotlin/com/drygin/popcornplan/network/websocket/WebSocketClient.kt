package com.drygin.popcornplan.network.websocket

import com.drygin.popcornplan.features.auth.domain.provider.TokenProvider
import com.drygin.popcornplan.network.api.HttpClientProvider
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class WebSocketClient(
    private val clientProvider: HttpClientProvider,
    private val json: Json,
    private val tokenProvider: TokenProvider,
    private val url: String = "ws://10.0.2.2:8080/sync/ws",
    //private val url: String = "ws://192.168.0.97:8080/sync/ws",
) {
    private var reconnectJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun start(onEvent: suspend (SyncEvent) -> Unit) {
        if (reconnectJob?.isActive == true) return

        reconnectJob = scope.launch {
            tokenProvider.jwtTokenFlow()
                .filterNotNull()
                .collect { token ->
                    connectWithRetry(token, onEvent)
                }
        }
    }

    private suspend fun connectWithRetry(token: String, onEvent: suspend (SyncEvent) -> Unit) {
        val client = clientProvider.getClient()
        while (true) {
            try {
                client.webSocket(
                    urlString = url,
                    request = {
                        header(HttpHeaders.Authorization, "Bearer $token")
                    }
                ) {
                    println("WebSocket connected")

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

                println("WebSocket завершён")
                break
            } catch (e: Exception) {
                if (isUnauthorizedException(e)) {
                    println("Токен истёк")
                    tokenProvider.clearToken()
                    break // ждём новый токен
                }

                println("Ошибка подключения: $e. Повтор через 5 сек...")
                delay(5000)
            }
        }
    }

    fun stop() {
        // TODO: Вызывать при логауте
        reconnectJob?.cancel()
        reconnectJob = null
    }

    private fun isUnauthorizedException(e: Throwable): Boolean {
        return e.message?.contains("Unauthorized", ignoreCase = true) == true
    }
}
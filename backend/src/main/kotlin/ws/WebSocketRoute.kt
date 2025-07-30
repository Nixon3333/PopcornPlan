package ws

import config.UserPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
fun Route.syncWebSocketRoute() {
    webSocket("/sync/ws") {
        val userId = call.principal<UserPrincipal>()?.userId
        if (userId == null) {
            close(
                CloseReason(
                    CloseReason.Codes.VIOLATED_POLICY,
                    "Unauthorized"
                )
            )
            return@webSocket
        }

        println("[$userId] WebSocket connected")
        WebSocketSessionRegistry.register(userId, this)

        try {
            for (frame in incoming) {
                try {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            println("[$userId] WebSocket received: $text")
                            // добавить парсинг
                        }

                        else -> {
                            println("[$userId] Unsupported frame type")
                            close(
                                CloseReason(
                                    CloseReason.Codes.CANNOT_ACCEPT,
                                    "Only text frames are supported"
                                )
                            )
                            break
                        }
                    }
                } catch (e: Exception) {
                    println("[$userId] Error while processing frame: ${e.message}")
                    close(
                        CloseReason(
                            CloseReason.Codes.INTERNAL_ERROR,
                            "Invalid message format"
                        )
                    )
                    break
                }
            }
        } catch (e: Exception) {
            println("[$userId] WebSocket session error: ${e.message}")
            close(
                CloseReason(
                    CloseReason.Codes.INTERNAL_ERROR,
                    "Internal server error"
                )
            )
        } finally {
            WebSocketSessionRegistry.unregister(userId, this)
            println("[$userId] WebSocket closed")
        }
    }
}
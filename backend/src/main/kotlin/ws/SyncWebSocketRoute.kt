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
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No user"))
            return@webSocket
        }

        WebSocketSessionRegistry.register(userId, this)

        try {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    println("[$userId] WebSocket received: ${frame.readText()}")
                }
            }
        } finally {
            WebSocketSessionRegistry.unregister(userId, this)
        }
    }
}
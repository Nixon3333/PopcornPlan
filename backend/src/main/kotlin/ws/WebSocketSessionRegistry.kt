package ws

import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.send
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
object WebSocketSessionRegistry {
    private val sessions = ConcurrentHashMap<String, MutableSet<DefaultWebSocketServerSession>>()

    fun register(userId: String, session: DefaultWebSocketServerSession) {
        sessions.computeIfAbsent(userId) { mutableSetOf() }.add(session)
    }

    fun unregister(userId: String, session: DefaultWebSocketServerSession) {
        sessions[userId]?.remove(session)
    }

    suspend fun sendToUser(userId: String, message: String) {
        sessions[userId]?.forEach { session ->
            session.send(message)
        }
    }
}
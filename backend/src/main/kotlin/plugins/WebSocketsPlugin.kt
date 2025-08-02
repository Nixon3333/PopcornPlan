package plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
fun Application.configureWebSockets() {
    install(WebSockets)
}
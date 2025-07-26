package auth

import config.JwtConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.authRoutes() {
    post("/auth/login") {
        val params = call.receiveParameters()
        val userId = params["userId"]

        if (userId.isNullOrBlank() || !AuthService.validateUser(userId)) {
            call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
            return@post
        }

        val token = JwtConfig.generateToken(userId)
        call.respond(mapOf("token" to token))
    }
}

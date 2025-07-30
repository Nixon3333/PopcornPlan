package auth

import com.drygin.popcornplan.features.auth.data.mapper.toDomain
import com.drygin.popcornplan.features.auth.data.mapper.toDto
import com.drygin.popcornplan.features.auth.data.remote.dto.LoginRequestDto
import com.drygin.popcornplan.features.auth.domain.model.Token
import config.JwtConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.authRoutes() {
    post("/auth/login") {
        val request = call.receive<LoginRequestDto>().toDomain()
        val userName = request.userName

        if (userName.isBlank() || !AuthService.validateUser(userName)) {
            call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
            return@post
        }

        val token = JwtConfig.generateToken(userName)
        val tokenResponse = Token(token).toDto()
        call.respond(tokenResponse)
    }
}

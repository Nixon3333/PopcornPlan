package routes

import config.UserPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import model.FavoriteDto
import storage.repository.FavoriteRepository

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.favoriteRoutes(
    favoriteRepository: FavoriteRepository
) {
    route("/favorites") {
        get {
            val userId = call.principal<UserPrincipal>()?.userId
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(favoriteRepository.getAll(userId))
        }

        post {
            val favoriteDTO = call.receive<FavoriteDto>()
            favoriteRepository.add(favoriteDTO)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{tmdbId}") {
            val tmdbId = call.parameters["tmdbId"]?.toIntOrNull()
            val userId = call.principal<UserPrincipal>()?.userId
            if (userId == null || tmdbId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            favoriteRepository.delete(userId, tmdbId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
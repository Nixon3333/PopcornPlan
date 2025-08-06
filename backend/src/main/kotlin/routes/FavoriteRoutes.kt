package routes

import com.drygin.popcornplan.features.sync.data.remote.dto.FavoriteDto
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
import service.FavoriteService

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.favoriteRoutes(
    favoriteService: FavoriteService
) {
    route("/favorites") {
        get {
            val userId = call.principal<UserPrincipal>()?.userId
                ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(favoriteService.getAll(userId))
        }

        post {
            val userId = call.principal<UserPrincipal>()?.userId
                ?: return@post call.respond(HttpStatusCode.Unauthorized)

            val favoriteDTO = call.receive<FavoriteDto>()
            favoriteService.add(userId, favoriteDTO.traktId)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{traktId}") {
            val userId = call.principal<UserPrincipal>()?.userId
                ?: return@delete call.respond(HttpStatusCode.Unauthorized)

            val tmdbId = call.parameters["traktId"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Missing or invalid traktId")

            favoriteService.delete(userId, tmdbId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
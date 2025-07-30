package routes

import config.UserPrincipal
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import service.SyncService

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.syncRoutes(syncService: SyncService) {
    get("/sync") {
        val userId = call.principal<UserPrincipal>()?.userId
            ?: throw IllegalArgumentException("Missing or invalid user")

        val result = syncService.getSyncData(userId)
        call.respond(result)
    }
}
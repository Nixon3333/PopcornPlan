package routes

import config.UserPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import model.SyncResponseDto
import storage.repository.FavoriteRepository
import storage.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.syncRoutes(
    reminderRepository: ReminderRepository,
    favoriteRepository: FavoriteRepository
) {
    get("/sync") {
        val userId = call.principal<UserPrincipal>()?.userId
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        call.respond(
            SyncResponseDto(
                favorites = favoriteRepository.getAll(userId),
                reminders = reminderRepository.getAll(userId)
            )
        )
    }
}
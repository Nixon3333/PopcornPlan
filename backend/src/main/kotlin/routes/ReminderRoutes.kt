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
import model.ReminderDto
import storage.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.reminderRoutes() {
    route("/reminders") {
        get {
            val userId = call.principal<UserPrincipal>()?.userId
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            call.respond(ReminderRepository.getAll(userId))
        }

        post {
            val reminderDto = call.receive<ReminderDto>()
            ReminderRepository.add(reminderDto)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{tmdbId}") {
            val tmdbId = call.parameters["tmdbId"]?.toIntOrNull()
            val userId = call.principal<UserPrincipal>()?.userId

            if (tmdbId != null && userId != null) {
                ReminderRepository.delete(userId, tmdbId)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
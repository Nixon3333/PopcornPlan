package routes

import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
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
import service.ReminderService

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun Route.reminderRoutes(reminderService: ReminderService) {
    route("/reminders") {
        get {
            val userId = call.principal<UserPrincipal>()?.userId
                ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(reminderService.getAll(userId))
        }

        post {
            val reminderDto = call.receive<ReminderDto>()
            reminderService.add(reminderDto)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{reminderId}") {
            val userId = call.principal<UserPrincipal>()?.userId
                ?: return@delete call.respond(HttpStatusCode.Unauthorized)
            val reminderId = call.parameters["reminderId"]
                ?: throw IllegalArgumentException("Missing reminderId")

            reminderService.delete(userId, reminderId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
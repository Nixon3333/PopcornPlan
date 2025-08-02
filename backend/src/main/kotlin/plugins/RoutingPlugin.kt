package plugins

import auth.authRoutes
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing
import routes.favoriteRoutes
import routes.reminderRoutes
import routes.syncRoutes
import service.FavoriteService
import service.ReminderService
import service.SyncService
import ws.syncWebSocketRoute

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
fun Application.configureRouting(
    reminderService: ReminderService,
    favoriteService: FavoriteService,
    syncService: SyncService
) {
    routing {
        authRoutes()
        authenticate("auth-jwt") {
            syncWebSocketRoute()
            favoriteRoutes(favoriteService)
            reminderRoutes(reminderService)
            syncRoutes(syncService)
        }
    }
}
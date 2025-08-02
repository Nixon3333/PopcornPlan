import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.getKoin
import plugins.configureAuthentication
import plugins.configureDatabase
import plugins.configureErrorHandling
import plugins.configureKoin
import plugins.configureRouting
import plugins.configureSerialization
import plugins.configureWebSockets
import service.FavoriteService
import service.ReminderService
import service.SyncService

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()

    val reminderService: ReminderService = getKoin().get()
    val favoriteService: FavoriteService = getKoin().get()
    val syncService: SyncService = getKoin().get()

    configureDatabase()
    configureErrorHandling()
    configureSerialization()
    configureWebSockets()
    configureAuthentication()
    configureRouting(reminderService, favoriteService, syncService)
}
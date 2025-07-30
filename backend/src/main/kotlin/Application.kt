import auth.authRoutes
import config.JwtConfig
import di.mainModule
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import plugins.configureErrorHandling
import routes.favoriteRoutes
import routes.reminderRoutes
import routes.syncRoutes
import service.FavoriteService
import service.ReminderService
import service.SyncService
import storage.DatabaseFactory
import ws.syncWebSocketRoute

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val appScope = this

    install(Koin) {
        modules(mainModule(appScope))
    }

    val reminderService: ReminderService = getKoin().get()
    val favoriteService: FavoriteService = getKoin().get()
    val syncService: SyncService = getKoin().get()

    DatabaseFactory.init()
    configureErrorHandling()

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }

    install(WebSockets)

    install(Authentication) {
        jwt("auth-jwt") {
            JwtConfig.configureKtorFeature(this)
        }
    }

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
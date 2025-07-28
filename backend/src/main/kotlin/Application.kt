import config.JwtConfig
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
import auth.authRoutes
import di.mainModule
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import routes.favoriteRoutes
import routes.reminderRoutes
import routes.syncRoutes
import storage.DatabaseFactory
import storage.repository.FavoriteRepository
import storage.repository.ReminderRepository
import ws.syncWebSocketRoute

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    install(Koin) {
        modules(mainModule)
    }

    val reminderRepository: ReminderRepository = getKoin().get()
    val favoriteRepository: FavoriteRepository = getKoin().get()

    DatabaseFactory.init()
    install(ContentNegotiation) {
        json()
    }

    install(Authentication) {
        jwt("auth-jwt") {
            JwtConfig.configureKtorFeature(this)
        }
    }

    routing {
        authRoutes()
        authenticate("auth-jwt") {
            syncWebSocketRoute()
            favoriteRoutes(favoriteRepository)
            reminderRoutes(reminderRepository)
            syncRoutes(reminderRepository, favoriteRepository)
        }
    }
}
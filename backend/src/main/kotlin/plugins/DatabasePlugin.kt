package plugins

import io.ktor.server.application.Application
import storage.DatabaseFactory

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
fun Application.configureDatabase() {
    DatabaseFactory.init()
}
package plugins

import di.mainModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
fun Application.configureKoin() {
    install(Koin) {
        modules(mainModule(this@configureKoin))
    }
}
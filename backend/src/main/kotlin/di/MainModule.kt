package di

import io.ktor.server.application.Application
import org.koin.dsl.module
import storage.repository.FavoriteRepository
import storage.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
val mainModule = module {
    single<Application> { get() }
    single { ReminderRepository(get()) }
    single { FavoriteRepository(get()) }
}
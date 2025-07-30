package di

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import service.FavoriteService
import service.ReminderService
import service.SyncService
import storage.repository.FavoriteRepository
import storage.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
fun mainModule(appScope: CoroutineScope) = module {
    single { appScope }
    single { ReminderRepository() }
    single { FavoriteRepository() }
    single {
        FavoriteService(
            repository = get(),
            wsRegistry = get(),
            appScope = get()
        )
    }
    single {
        ReminderService(
            repository = get(),
            wsRegistry = get(),
            appScope = get()
        )
    }
    single { SyncService(get(), get()) }
}
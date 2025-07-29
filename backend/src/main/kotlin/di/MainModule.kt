package di

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module
import storage.repository.FavoriteRepository
import storage.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
fun mainModule(appScope: CoroutineScope) = module {
    single { appScope }
    single { ReminderRepository(get()) }
    single { FavoriteRepository(get()) }
}
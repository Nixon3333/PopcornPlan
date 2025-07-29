package com.drygin.popcornplan.app.di

import com.drygin.popcornplan.BuildConfig
import com.drygin.popcornplan.common.domain.reminder.scheduler.ReminderScheduler
import com.drygin.popcornplan.core.notifications.AndroidReminderScheduler
import com.drygin.popcornplan.platform.AndroidTimeProvider
import com.drygin.popcornplan.platform.TimeProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 23.07.2025.
 */
val platformModule = module {
    single<String>(qualifier = named("traktApiKey")) {
        BuildConfig.TRAKT_API_KEY
    }
    single<TimeProvider> { AndroidTimeProvider() }
    single<ReminderScheduler> { AndroidReminderScheduler(get()) }
}

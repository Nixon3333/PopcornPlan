package com.drygin.popcornplan.app.di

import com.drygin.popcornplan.common.domain.reminder.scheduler.NotificationScheduler
import com.drygin.popcornplan.common.domain.reminder.usecase.CancelNotificationUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.NotificationUseCases
import com.drygin.popcornplan.common.domain.reminder.usecase.ShowReminderNotificationUseCase
import com.drygin.popcornplan.core.notifications.AndroidNotificationScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 24.07.2025.
 */
val notificationModule = module {
    single<NotificationScheduler> { AndroidNotificationScheduler(androidContext()) }

    factory { ShowReminderNotificationUseCase(get()) }
    factory { CancelNotificationUseCase(get()) }

    factory {
        NotificationUseCases(
            showReminder = get(),
            cancel = get()
        )
    }
}
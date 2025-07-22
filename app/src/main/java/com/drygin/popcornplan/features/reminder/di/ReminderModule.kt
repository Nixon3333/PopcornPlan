package com.drygin.popcornplan.features.reminder.di

import com.drygin.popcornplan.common.data.scheduler.AndroidReminderScheduler
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.common.domain.reminder.scheduler.ReminderScheduler
import com.drygin.popcornplan.common.domain.reminder.usecase.AddReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.CancelReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.GetRemindersUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.ReminderUseCases
import com.drygin.popcornplan.common.domain.reminder.usecase.RemoveReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.ScheduleReminderUseCase
import com.drygin.popcornplan.features.reminder.data.repository.ReminderRepositoryImpl
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
val reminderModule = module {
    single<ReminderRepository> { ReminderRepositoryImpl(get()) }

    single<ReminderScheduler> { AndroidReminderScheduler(get()) }

    single {
        ReminderUseCases(
            getReminders = GetRemindersUseCase(get()),
            addReminder = AddReminderUseCase(get()),
            removeReminder = RemoveReminderUseCase(get()),
            scheduleReminder = ScheduleReminderUseCase(get()),
            cancelReminder = CancelReminderUseCase(get())
        )
    }
}
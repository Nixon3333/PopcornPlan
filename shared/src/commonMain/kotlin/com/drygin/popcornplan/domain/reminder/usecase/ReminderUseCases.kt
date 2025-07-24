package com.drygin.popcornplan.common.domain.reminder.usecase

import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.common.domain.reminder.scheduler.ReminderScheduler
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class ReminderUseCases(
    val getReminders: GetRemindersUseCase,
    val addReminder: AddReminderUseCase,
    val removeReminder: RemoveReminderUseCase,
    val scheduleReminder: ScheduleReminderUseCase,
    val cancelReminder: CancelReminderUseCase
)

class GetRemindersUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> = repository.getAll()
}

class AddReminderUseCase(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.addReminder(reminder)
    }
}

class RemoveReminderUseCase(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String) {
        repository.deleteReminder(reminderId)
    }
}

class ScheduleReminderUseCase(
    private val schedule: ReminderScheduler
) {
    operator fun invoke(reminder: Reminder) = schedule.schedule(reminder)
}

class CancelReminderUseCase(
    private val schedule: ReminderScheduler
) {
    operator fun invoke(reminder: Reminder) = schedule.cancel(reminder)
}

package com.drygin.popcornplan.common.domain.reminder.usecase

import com.drygin.popcornplan.common.domain.reminder.scheduler.NotificationScheduler

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class NotificationUseCases(
    val showReminder: ShowReminderNotificationUseCase,
    val cancel: CancelNotificationUseCase
)

class ShowReminderNotificationUseCase(
    private val scheduler: NotificationScheduler
) {
    operator fun invoke(id: Int, title: String, message: String) {
        scheduler.showReminderNotification(id, title, message)
    }
}

class CancelNotificationUseCase(
    private val scheduler: NotificationScheduler
) {
    operator fun invoke(id: Int) {
        scheduler.cancelNotification(id)
    }
}

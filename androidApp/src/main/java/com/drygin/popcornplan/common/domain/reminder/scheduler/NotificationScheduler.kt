package com.drygin.popcornplan.common.domain.reminder.scheduler

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
interface NotificationScheduler {
    fun showReminderNotification(id: Int, title: String, message: String)
    fun cancelNotification(id: Int)
}
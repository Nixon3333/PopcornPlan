package com.drygin.popcornplan.common.domain.reminder.scheduler

import com.drygin.popcornplan.common.domain.reminder.model.Reminder

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
interface ReminderScheduler {
    fun schedule(reminder: Reminder)
    fun cancel(reminder: Reminder)
}
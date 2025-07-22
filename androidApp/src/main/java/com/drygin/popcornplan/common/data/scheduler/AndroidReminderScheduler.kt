package com.drygin.popcornplan.common.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.common.domain.reminder.scheduler.ReminderScheduler
import com.drygin.popcornplan.features.reminder.utils.ReminderReceiver

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
class AndroidReminderScheduler(
    private val context: Context
): ReminderScheduler {

    override fun schedule(reminder: Reminder) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", reminder.title)
            //putExtra("reminderId", reminder.id)
            //putExtra("tmdbId", reminder.traktId)
            //putExtra("type", reminder.type)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAtMillis = reminder.reminderTime

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(reminder: Reminder) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
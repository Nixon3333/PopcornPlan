package com.drygin.popcornplan.features.reminder.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.drygin.popcornplan.features.reminder.domain.model.Reminder

/**
 * Created by Drygin Nikita on 29.06.2025.
 */
object ReminderScheduler {
    fun scheduleReminder(context: Context, reminder: Reminder) {
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
}
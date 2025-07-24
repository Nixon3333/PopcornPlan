package com.drygin.popcornplan.core.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.drygin.popcornplan.R
import com.drygin.popcornplan.common.domain.reminder.scheduler.NotificationScheduler

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
class AndroidNotificationScheduler(
    private val context: Context
) : NotificationScheduler {

    companion object {
        private const val CHANNEL_ID = "reminders_channel"
        private const val CHANNEL_NAME = "Напоминания"
        private const val CHANNEL_DESC = "Уведомления о фильмах и сериалах"
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun showReminderNotification(id: Int, title: String, message: String) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)
    }

    override fun cancelNotification(id: Int) {
        NotificationManagerCompat.from(context).cancel(id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
                enableLights(true)
                enableVibration(true)
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}
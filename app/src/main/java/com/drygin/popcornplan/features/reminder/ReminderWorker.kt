package com.drygin.popcornplan.features.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.drygin.popcornplan.R

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
const val REMINDER_NOTIFICATION_CHANNEL_ID = "reminders"
const val REMINDER_NOTIFICATION_ID = 1

@HiltWorker
class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    init {
        createReminderNotificationChannel(applicationContext)
    }

    @SuppressLint("MissingPermission") // TODO: Добавить проверку, убрать suppress
    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: return Result.failure()

        val notification = NotificationCompat
            .Builder(
                applicationContext,
                REMINDER_NOTIFICATION_CHANNEL_ID
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("PopcornPlan: Напоминание")
            .setContentText("Пора посмотреть: $title")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(REMINDER_NOTIFICATION_ID, notification)

        return Result.success()
    }

    private fun createReminderNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            REMINDER_NOTIFICATION_CHANNEL_ID,
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder notifications"
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
package com.drygin.popcornplan.features.reminder.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.drygin.popcornplan.common.utils.NotificationHelper

/**
 * Created by Drygin Nikita on 29.06.2025.
 */
class ReminderReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: return
        val message = intent.getStringExtra("message") ?: "Напоминание"

        NotificationHelper.showReminderNotification(context, title, message)
    }
}
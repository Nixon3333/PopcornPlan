package com.drygin.popcornplan.features.reminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val tmdbId: Int,
    val title: String,
    val type: String,
    val reminderTime: Long, // millis (System.currentTimeMillis() + offset)
    val posterUrl: String?,
    val createdAt: Long = System.currentTimeMillis()
)

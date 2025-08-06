package com.drygin.popcornplan.reatures.reminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val traktId: Int,
    val title: String,
    val type: String,
    val description: String,
    val reminderTime: Long,
    //val posterUrl: String?,
    val createdAt: Long
)
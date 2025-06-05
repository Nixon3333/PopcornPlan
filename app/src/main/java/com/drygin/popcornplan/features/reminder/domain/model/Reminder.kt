package com.drygin.popcornplan.features.reminder.domain.model

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
data class Reminder(
    val id: String,
    val tmdbId: Int,
    val title: String,
    val type: String,
    val reminderTime: Long, // millis (System.currentTimeMillis() + offset)
    val posterUrl: String?,
    val createdAt: Long
)

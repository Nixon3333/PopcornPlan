package com.drygin.popcornplan.features.reminder.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
@Serializable
data class ReminderDto(
    val id: String,
    val userId: String,
    val tmdbId: Int,
    val title: String,
    val type: String,
    val reminderTime: Long,
    //val posterUrl: String? = null,
    val createdAt: Long
)
package model

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
@Serializable
data class ReminderDto(
    val userId: String,
    val tmdbId: Int,
    val type: String,
    val reminderTime: Long,
    val createdAt: Long
)

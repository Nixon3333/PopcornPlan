package com.drygin.popcornplan.features.sync.data.remote.dto

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.features.reminder.data.remote.dto.ReminderDto
import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
@Serializable
data class SyncResponseDto(
    val favorites: List<FavoriteDto>,
    val reminders: List<ReminderDto>
)
package com.drygin.popcornplan.features.sync.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
@Serializable
data class SyncResponseDto(
    val favorites: List<FavoriteDto>,
    val reminders: List<ReminderDto>
)
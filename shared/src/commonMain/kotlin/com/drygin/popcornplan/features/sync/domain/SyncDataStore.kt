package com.drygin.popcornplan.features.sync.domain

import com.drygin.popcornplan.features.sync.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
interface SyncDataStore {
    suspend fun saveFavorites(favorites: List<FavoriteDto>)
    suspend fun saveReminders(reminders: List<ReminderDto>)
}

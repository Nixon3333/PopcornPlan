package com.drygin.popcornplan.platform

import com.drygin.popcornplan.data.local.dao.FavoriteDao
import com.drygin.popcornplan.reatures.sync.data.mapper.toEntity
import com.drygin.popcornplan.features.sync.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
import com.drygin.popcornplan.features.sync.domain.SyncDataStore
import com.drygin.popcornplan.reatures.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.reatures.reminder.data.mapper.toEntity

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
class AndroidSyncDataStore(
    private val favoriteDao: FavoriteDao,
    private val reminderDao: ReminderDao
) : SyncDataStore {

    override suspend fun saveFavorites(favorites: List<FavoriteDto>) {
        favoriteDao.clearAll()
        favoriteDao.insertAll(favorites.map { it.toEntity() })
    }

    override suspend fun saveReminders(reminders: List<ReminderDto>) {
        reminderDao.clearAll()
        reminderDao.insertAll(reminders.map { it.toEntity() })
    }
}

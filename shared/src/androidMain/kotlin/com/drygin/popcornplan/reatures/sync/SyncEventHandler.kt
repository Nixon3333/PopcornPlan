package com.drygin.popcornplan.reatures.sync

import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.features.sync.domain.SyncEvent

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class SyncEventHandler(
    private val favoriteRepository: FavoriteRepository,
    private val reminderRepository: ReminderRepository
) {
    suspend fun handle(event: SyncEvent) {
        when (event) {
            is SyncEvent.FavoriteAdded -> favoriteRepository.insert(event.tmdbId)
            is SyncEvent.FavoriteRemoved -> favoriteRepository.delete(event.tmdbId)
            is SyncEvent.ReminderAdded -> reminderRepository.insert(event.reminder)
            is SyncEvent.ReminderRemoved -> reminderRepository.deleteReminder(event.reminderId)
        }
    }
}
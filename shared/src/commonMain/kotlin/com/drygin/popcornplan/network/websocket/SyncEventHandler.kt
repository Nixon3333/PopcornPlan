package com.drygin.popcornplan.network.websocket

import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class SyncEventHandler(
    private val favoriteRepository: FavoriteRepository,
    private val reminderRepository: ReminderRepository
) {
    suspend fun handle(event: SyncEvent) {
        when (event) {
            is SyncEvent.FavoriteAdded -> favoriteRepository.setFavorite(event.traktId, true)
            is SyncEvent.FavoriteRemoved -> favoriteRepository.setFavorite(event.traktId, false)
            is SyncEvent.ReminderAdded -> reminderRepository.insert(event.reminder)
            is SyncEvent.ReminderRemoved -> reminderRepository.deleteReminder(event.reminderId)
        }
    }
}
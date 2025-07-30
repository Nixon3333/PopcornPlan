package service

import com.drygin.popcornplan.features.sync.data.remote.dto.SyncResponseDto

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
class SyncService(
    private val favoriteService: FavoriteService,
    private val reminderService: ReminderService
) {
    fun getSyncData(userId: String): SyncResponseDto {
        val favorites = favoriteService.getAll(userId)
        val reminders = reminderService.getAll(userId)
        return SyncResponseDto(
            favorites = favorites,
            reminders = reminders
        )
    }
}
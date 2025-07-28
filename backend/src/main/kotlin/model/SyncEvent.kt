package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
@Serializable
sealed interface SyncEvent {
    @Serializable
    @SerialName("reminder_added")
    data class ReminderAdded(val reminder: ReminderDto) : SyncEvent

    @Serializable
    @SerialName("reminder_removed")
    data class ReminderRemoved(val tmdbId: Int) : SyncEvent

    @Serializable
    @SerialName("favorite_added")
    data class FavoriteAdded(val favorite: FavoriteDto) : SyncEvent

    @Serializable
    @SerialName("favorite_removed")
    data class FavoriteRemoved(val tmdbId: Int) : SyncEvent
}
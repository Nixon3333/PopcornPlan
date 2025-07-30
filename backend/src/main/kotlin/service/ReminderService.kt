package service

import com.drygin.popcornplan.features.reminder.data.remote.dto.ReminderDto
import com.drygin.popcornplan.network.websocket.SyncEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import storage.repository.ReminderRepository
import ws.WebSocketSessionRegistry

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
class ReminderService(
    private val repository: ReminderRepository,
    private val wsRegistry: WebSocketSessionRegistry,
    private val appScope: CoroutineScope
) {
    fun getAll(userId: String): List<ReminderDto> {
        return repository.getAll(userId)
    }

    fun add(reminder: ReminderDto) {
        repository.upsert(reminder)

        appScope.launch {
            wsRegistry.sendToUser(
                reminder.userId,
                Json.encodeToString(
                    SyncEvent.serializer(),
                    SyncEvent.ReminderAdded(reminder)
                )
            )
        }
    }

    fun delete(userId: String, reminderId: String) {
        repository.delete(userId, reminderId)

        appScope.launch {
            wsRegistry.sendToUser(
                userId,
                Json.encodeToString(
                    SyncEvent.serializer(),
                    SyncEvent.ReminderRemoved(reminderId)
                )
            )
        }
    }
}
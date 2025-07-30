package service

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.network.websocket.SyncEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import storage.repository.FavoriteRepository
import ws.WebSocketSessionRegistry

/**
 * Created by Drygin Nikita on 30.07.2025.
 */
class FavoriteService(
    private val repository: FavoriteRepository,
    private val wsRegistry: WebSocketSessionRegistry,
    private val appScope: CoroutineScope
) {

    fun getAll(userId: String): List<FavoriteDto> {
        return repository.getAll(userId)
    }

    fun add(userId: String, tmdbId: Int) {
        repository.upsert(userId, tmdbId)

        appScope.launch {
            wsRegistry.sendToUser(
                userId,
                Json.encodeToString(
                    SyncEvent.serializer(),
                    SyncEvent.FavoriteAdded(tmdbId)
                )
            )
        }
    }

    fun delete(userId: String, tmdbId: Int) {
        repository.delete(userId, tmdbId)

        appScope.launch {
            wsRegistry.sendToUser(
                userId,
                Json.encodeToString(
                    SyncEvent.serializer(),
                    SyncEvent.FavoriteRemoved(tmdbId)
                )
            )
        }
    }
}
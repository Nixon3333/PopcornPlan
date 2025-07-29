package storage.repository

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import com.drygin.popcornplan.features.sync.domain.SyncEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import storage.db.Favorites
import ws.WebSocketSessionRegistry

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
class FavoriteRepository(private val appScope: CoroutineScope) {

    fun getAll(userId: String): List<FavoriteDto> = transaction {
        Favorites.selectAll().where { Favorites.userId eq userId }
            .map {
                FavoriteDto(
                    //userId = it[Favorites.userId],
                    tmdbId = it[Favorites.tmdbId],
                    //createdAt = it[Favorites.createdAt]
                )
            }
    }

    fun add(userId: String, tmdbId: Int) = transaction {
        val updatedRows = Favorites.update(
            where = {
                (Favorites.userId eq userId) and
                        (Favorites.tmdbId eq tmdbId)
            }
        ) {
            it[createdAt] = System.currentTimeMillis()
        }

        if (updatedRows == 0) {
            Favorites.insert {
                it[Favorites.userId] = userId
                it[Favorites.tmdbId] = tmdbId
                it[createdAt] = System.currentTimeMillis()
            }
        }

        appScope.launch {
            WebSocketSessionRegistry.sendToUser(
                userId,
                Json.encodeToString(SyncEvent.FavoriteAdded(tmdbId))
            )
        }
    }

    fun delete(userId: String, tmdbId: Int) = transaction {
        Favorites.deleteWhere {
            (Favorites.userId eq userId) and (Favorites.tmdbId eq tmdbId)
        }

        appScope.launch {
            WebSocketSessionRegistry.sendToUser(
                userId,
                Json.encodeToString(SyncEvent.FavoriteRemoved(tmdbId))
            )
        }
    }
}
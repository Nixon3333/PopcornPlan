package storage.repository

import io.ktor.server.application.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.FavoriteDto
import model.SyncEvent
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
class FavoriteRepository(private val app: Application) {

    fun getAll(userId: String): List<FavoriteDto> = transaction {
        Favorites.selectAll().where { Favorites.userId eq userId }
            .map {
                FavoriteDto(
                    userId = it[Favorites.userId],
                    tmdbId = it[Favorites.tmdbId],
                    createdAt = it[Favorites.createdAt]
                )
            }
    }

    fun add(favorite: FavoriteDto) = transaction {
        val updatedRows = Favorites.update(
            where = {
                (Favorites.userId eq favorite.userId) and
                        (Favorites.tmdbId eq favorite.tmdbId)
            }
        ) {
            it[createdAt] = favorite.createdAt
        }

        if (updatedRows == 0) {
            Favorites.insert {
                it[userId] = favorite.userId
                it[tmdbId] = favorite.tmdbId
                it[createdAt] = favorite.createdAt
            }
        }

        app.launch {
            WebSocketSessionRegistry.sendToUser(
                favorite.userId,
                Json.encodeToString(SyncEvent.FavoriteAdded(favorite))
            )
        }
    }

    fun delete(userId: String, tmdbId: Int) = transaction {
        Favorites.deleteWhere {
            (Favorites.userId eq userId) and (Favorites.tmdbId eq tmdbId)
        }

        app.launch {
            WebSocketSessionRegistry.sendToUser(
                userId,
                Json.encodeToString(SyncEvent.FavoriteRemoved(tmdbId))
            )
        }
    }
}
package storage.repository

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import storage.db.Favorites

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
class FavoriteRepository {
    fun getAll(userId: String): List<FavoriteDto> = transaction {
        Favorites.selectAll().where { Favorites.userId eq userId }
            .map {
                FavoriteDto(
                    tmdbId = it[Favorites.tmdbId]
                )
            }
    }

    fun upsert(userId: String, tmdbId: Int) = transaction {
        val updated = Favorites.update(
            where = { (Favorites.userId eq userId) and (Favorites.tmdbId eq tmdbId) }
        ) {
            it[createdAt] = System.currentTimeMillis()
        }

        if (updated == 0) {
            Favorites.insert {
                it[Favorites.userId] = userId
                it[Favorites.tmdbId] = tmdbId
                it[createdAt] = System.currentTimeMillis()
            }
        }
    }

    fun delete(userId: String, tmdbId: Int) = transaction {
        Favorites.deleteWhere {
            (Favorites.userId eq userId) and (Favorites.tmdbId eq tmdbId)
        }
    }
}
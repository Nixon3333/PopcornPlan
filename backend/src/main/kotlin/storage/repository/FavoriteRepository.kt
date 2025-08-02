package storage.repository

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
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
                    tmdbId = it[Favorites.traktId]
                )
            }
    }

    fun upsert(userId: String, traktId: Int) = transaction {
        val insertResult = Favorites.insertIgnore {
            it[Favorites.userId] = userId
            it[Favorites.traktId] = traktId
            it[createdAt] = System.currentTimeMillis()
        }

        if (insertResult.insertedCount == 0) {
            Favorites.update(
                where = { (Favorites.userId eq userId) and (Favorites.traktId eq traktId) }
            ) {
                it[createdAt] = System.currentTimeMillis()
            }
        }
    }

    fun delete(userId: String, traktId: Int) = transaction {
        Favorites.deleteWhere {
            (Favorites.userId eq userId) and (Favorites.traktId eq traktId)
        }
    }
}
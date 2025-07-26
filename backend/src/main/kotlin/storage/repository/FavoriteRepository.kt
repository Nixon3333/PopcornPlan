package storage.repository

import model.FavoriteDto
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
object FavoriteRepository {

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
    }

    fun delete(userId: String, tmdbId: Int) = transaction {
        Favorites.deleteWhere {
            (Favorites.userId eq userId) and (Favorites.tmdbId eq tmdbId)
        }
    }
}
package storage.db

import org.jetbrains.exposed.sql.Table

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object Favorites : Table("favorites") {
    val traktId = integer("trakt_id")
    val userId = varchar("user_id", 50)
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(userId, traktId)
}
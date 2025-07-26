package storage.db

import org.jetbrains.exposed.sql.Table

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object Reminders : Table("reminders") {
    val id = integer("id").autoIncrement()
    val tmdbId = integer("tmdb_id")
    val userId = varchar("user_id", 50)
    val type = varchar("type", 10)
    val reminderTime = long("reminder_time")
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}
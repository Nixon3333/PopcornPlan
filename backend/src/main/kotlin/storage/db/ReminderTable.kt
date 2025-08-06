package storage.db

import org.jetbrains.exposed.sql.Table

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object Reminders : Table("reminders") {
    val id = varchar("id", 36)
    val traktId = integer("trakt_id")
    val userId = varchar("user_id", 50)
    val title = varchar("title", 50)
    val type = varchar("type", 10)
    val reminderTime = long("reminder_time")
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}
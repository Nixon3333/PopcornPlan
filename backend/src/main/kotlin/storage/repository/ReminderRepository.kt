package storage.repository

import model.ReminderDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import storage.db.Reminders

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object ReminderRepository {

    fun getAll(userId: String): List<ReminderDto> = transaction {
        Reminders.selectAll().where { Reminders.userId eq userId }
            .map {
                ReminderDto(
                    userId = it[Reminders.userId],
                    tmdbId = it[Reminders.tmdbId],
                    type = it[Reminders.type],
                    reminderTime = it[Reminders.reminderTime],
                    createdAt = it[Reminders.createdAt]
                )
            }
    }

    fun add(reminder: ReminderDto) = transaction {
        Reminders.deleteWhere {
            (Reminders.userId eq reminder.userId) and (Reminders.tmdbId eq reminder.tmdbId)
        }

        Reminders.insert {
            it[userId] = reminder.userId
            it[tmdbId] = reminder.tmdbId
            it[type] = reminder.type
            it[reminderTime] = reminder.reminderTime
            it[createdAt] = reminder.createdAt
        }
    }

    fun delete(userId: String, tmdbId: Int) = transaction {
        Reminders.deleteWhere {
            (Reminders.userId eq userId) and (Reminders.tmdbId eq tmdbId)
        }
    }
}
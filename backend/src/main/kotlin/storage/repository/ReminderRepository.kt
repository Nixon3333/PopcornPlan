package storage.repository

import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
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
class ReminderRepository {

    fun getAll(userId: String): List<ReminderDto> = transaction {
        Reminders.selectAll().where { Reminders.userId eq userId }
            .map {
                ReminderDto(
                    id = it[Reminders.id],
                    userId = it[Reminders.userId],
                    traktId = it[Reminders.traktId],
                    title = it[Reminders.title],
                    type = it[Reminders.type],
                    reminderTime = it[Reminders.reminderTime],
                    createdAt = it[Reminders.createdAt]
                )
            }
    }

    fun upsert(reminder: ReminderDto) = transaction {
        Reminders.deleteWhere {
            (Reminders.userId eq reminder.userId) and (Reminders.traktId eq reminder.traktId)
        }

        Reminders.insert {
            it[id] = reminder.id
            it[userId] = reminder.userId
            it[traktId] = reminder.traktId
            it[title] = reminder.title
            it[type] = reminder.type
            it[reminderTime] = reminder.reminderTime
            it[createdAt] = reminder.createdAt
        }
    }

    fun delete(userId: String, reminderId: String) = transaction {
        Reminders.deleteWhere {
            (Reminders.userId eq userId) and (Reminders.id eq reminderId)
        }
    }
}
package storage.repository

import com.drygin.popcornplan.features.reminder.data.remote.dto.ReminderDto
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
import storage.db.Reminders
import ws.WebSocketSessionRegistry

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
class ReminderRepository(private val appScope: CoroutineScope) {

    fun getAll(userId: String): List<ReminderDto> = transaction {
        Reminders.selectAll().where { Reminders.userId eq userId }
            .map {
                ReminderDto(
                    id = it[Reminders.id],
                    userId = it[Reminders.userId],
                    tmdbId = it[Reminders.tmdbId],
                    title = it[Reminders.title],
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

        appScope.launch {
            WebSocketSessionRegistry.sendToUser(
                reminder.userId,
                Json.encodeToString(SyncEvent.ReminderAdded(reminder))
            )
        }
    }

    fun delete(userId: String, reminderId: String) = transaction {
        Reminders.deleteWhere {
            (Reminders.userId eq userId) and (Reminders.id eq reminderId)
        }

        appScope.launch {
            WebSocketSessionRegistry.sendToUser(
                userId,
                Json.encodeToString(SyncEvent.ReminderRemoved(reminderId))
            )
        }
    }
}
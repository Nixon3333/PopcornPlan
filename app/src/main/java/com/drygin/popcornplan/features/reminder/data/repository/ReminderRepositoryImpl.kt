package com.drygin.popcornplan.features.reminder.data.repository

import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.features.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.features.reminder.data.mapper.toDomain
import com.drygin.popcornplan.features.reminder.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
class ReminderRepositoryImpl(
    private val dao: ReminderDao
) : ReminderRepository {
    override fun getAll(): Flow<List<Reminder>> = dao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }
    override suspend fun addReminder(reminder: Reminder) = dao.insert(reminder.toEntity())

    override suspend fun deleteReminder(id: String) = dao.delete(id)
}
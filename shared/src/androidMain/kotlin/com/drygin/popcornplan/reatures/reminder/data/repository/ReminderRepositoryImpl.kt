package com.drygin.popcornplan.reatures.reminder.data.repository

import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
import com.drygin.popcornplan.reatures.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.reatures.reminder.data.mapper.toDomain
import com.drygin.popcornplan.reatures.reminder.data.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

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

    override suspend fun insert(dto: ReminderDto) {
        withContext(Dispatchers.IO) {
            dao.insert(dto.toEntity())
        }
    }

    override suspend fun delete(reminderId: String) {
        withContext(Dispatchers.IO) {
            dao.delete(reminderId)
        }
    }
}
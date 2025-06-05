package com.drygin.popcornplan.features.reminder.data.repository

import com.drygin.popcornplan.features.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.features.reminder.data.mapper.toDomain
import com.drygin.popcornplan.features.reminder.data.mapper.toEntity
import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import com.drygin.popcornplan.features.reminder.domain.repository.IReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : IReminderRepository {
    override fun getAll(): Flow<List<Reminder>> = dao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }
    override suspend fun addReminder(reminder: Reminder) = dao.insert(reminder.toEntity())

    override suspend fun deleteReminder(id: String) = dao.delete(id)
}
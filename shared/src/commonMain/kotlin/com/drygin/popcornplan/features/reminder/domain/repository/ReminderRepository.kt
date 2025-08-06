package com.drygin.popcornplan.common.domain.reminder.repository

import com.drygin.popcornplan.common.domain.reminder.model.Reminder
import com.drygin.popcornplan.features.sync.data.remote.dto.ReminderDto
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
interface ReminderRepository {
    fun getAll(): Flow<List<Reminder>>
    suspend fun addReminder(reminder: Reminder)
    suspend fun deleteReminder(id: String)
    suspend fun insert(dto: ReminderDto)
    suspend fun delete(reminderId: String)
}
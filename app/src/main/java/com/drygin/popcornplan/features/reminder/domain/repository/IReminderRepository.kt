package com.drygin.popcornplan.features.reminder.domain.repository

import com.drygin.popcornplan.features.reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
interface IReminderRepository {
    fun getAll(): Flow<List<Reminder>>
    suspend fun addReminder(reminder: Reminder)
    suspend fun deleteReminder(id: String)
}
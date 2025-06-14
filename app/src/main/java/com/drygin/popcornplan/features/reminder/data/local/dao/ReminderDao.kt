package com.drygin.popcornplan.features.reminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drygin.popcornplan.features.reminder.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders ORDER BY reminderTime ASC")
    fun getAll(): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    suspend fun get(id: String): ReminderEntity?
}
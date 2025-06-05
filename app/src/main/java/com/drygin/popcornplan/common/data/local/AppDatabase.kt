package com.drygin.popcornplan.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.features.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.features.reminder.data.local.entity.ReminderEntity

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Database(entities = [MovieEntity::class, ReminderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun reminderDao(): ReminderDao
}
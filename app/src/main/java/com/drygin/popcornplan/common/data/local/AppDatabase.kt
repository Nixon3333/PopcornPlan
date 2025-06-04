package com.drygin.popcornplan.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.entity.MovieEntity

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
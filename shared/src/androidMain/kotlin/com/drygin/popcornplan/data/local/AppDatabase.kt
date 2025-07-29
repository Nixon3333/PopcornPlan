package com.drygin.popcornplan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drygin.popcornplan.data.local.dao.ImageDao
import com.drygin.popcornplan.data.local.dao.MovieDao
import com.drygin.popcornplan.reatures.trending.data.local.dao.TrendingDao
import com.drygin.popcornplan.data.local.entity.EpisodeEntity
import com.drygin.popcornplan.data.local.entity.ImageEntity
import com.drygin.popcornplan.data.local.entity.MovieEntity
import com.drygin.popcornplan.data.local.entity.SeasonEntity
import com.drygin.popcornplan.data.local.entity.ShowEntity
import com.drygin.popcornplan.reatures.trending.data.local.entity.TrendingMovieEntity
import com.drygin.popcornplan.reatures.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.reatures.reminder.data.local.entity.ReminderEntity

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Database(
    entities =
    [MovieEntity::class, ReminderEntity::class, EpisodeEntity::class, ImageEntity::class,
        SeasonEntity::class, ShowEntity::class, TrendingMovieEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun reminderDao(): ReminderDao
    abstract fun imageDao(): ImageDao
    abstract fun trendingDao(): TrendingDao
}
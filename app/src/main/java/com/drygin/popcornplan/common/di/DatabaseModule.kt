package com.drygin.popcornplan.common.di

import android.content.Context
import androidx.room.Room
import com.drygin.popcornplan.common.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pp_database"
        )
            .fallbackToDestructiveMigration() // TODO: Только для дебага
            .build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    @Provides
    fun provideReminderDao(database: AppDatabase) = database.reminderDao()

    @Provides
    fun provideImageDao(database: AppDatabase) = database.imageDao()

    @Provides
    fun provideTrendingDao(database: AppDatabase) = database.trendingDao()
}
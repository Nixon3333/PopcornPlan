package com.drygin.popcornplan.di

import androidx.room.Room
import com.drygin.popcornplan.data.local.AppDatabase
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 30.05.2025.
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "pp_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().reminderDao() }
    single { get<AppDatabase>().imageDao() }
    single { get<AppDatabase>().trendingDao() }
    single { get<AppDatabase>().favoriteDao() }
}
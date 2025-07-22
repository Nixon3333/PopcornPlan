package com.drygin.popcornplan.common.di

import androidx.room.Room
import com.drygin.popcornplan.common.data.local.AppDatabase
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
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().reminderDao() }
    single { get<AppDatabase>().imageDao() }
    single { get<AppDatabase>().trendingDao() }
}
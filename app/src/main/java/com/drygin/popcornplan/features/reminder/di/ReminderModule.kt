package com.drygin.popcornplan.features.reminder.di

import com.drygin.popcornplan.features.reminder.data.local.dao.ReminderDao
import com.drygin.popcornplan.features.reminder.data.repository.ReminderRepositoryImpl
import com.drygin.popcornplan.features.reminder.domain.repository.IReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {
    @Provides
    @Singleton
    fun provideReminderRepository(dao: ReminderDao): IReminderRepository =
        ReminderRepositoryImpl(dao)
}
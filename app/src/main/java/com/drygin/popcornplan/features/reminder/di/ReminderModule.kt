package com.drygin.popcornplan.features.reminder.di

import com.drygin.popcornplan.features.reminder.data.repository.ReminderRepositoryImpl
import com.drygin.popcornplan.features.reminder.domain.repository.IReminderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 04.06.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {
    @Binds
    @Singleton
    abstract fun bindReminderRepository(repoImpl: ReminderRepositoryImpl): IReminderRepository
}
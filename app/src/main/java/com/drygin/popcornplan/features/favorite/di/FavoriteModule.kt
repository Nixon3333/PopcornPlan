package com.drygin.popcornplan.features.favorite.di

import com.drygin.popcornplan.common.data.repository.FavoriteRepositoryImpl
import com.drygin.popcornplan.common.domain.repository.IFavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {
    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(repoImpl: FavoriteRepositoryImpl) : IFavoriteRepository
}
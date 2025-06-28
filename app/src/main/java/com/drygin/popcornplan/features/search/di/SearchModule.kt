package com.drygin.popcornplan.features.search.di

import com.drygin.popcornplan.features.search.data.api.SearchApi
import com.drygin.popcornplan.features.search.data.repository.SearchRepositoryImpl
import com.drygin.popcornplan.features.search.domain.repository.ISearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class SearchModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(repoImpl: SearchRepositoryImpl) : ISearchRepository

    companion object {
        @Provides
        @Singleton
        fun provideSearchApi(retrofit: Retrofit): SearchApi {
            return retrofit.create(SearchApi::class.java)
        }
    }
}
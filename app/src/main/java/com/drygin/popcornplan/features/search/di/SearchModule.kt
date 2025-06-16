package com.drygin.popcornplan.features.search.di

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.features.search.data.api.SearchApi
import com.drygin.popcornplan.features.search.data.repository.SearchRepositoryImpl
import com.drygin.popcornplan.features.search.domain.repository.ISearchRepository
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
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: SearchApi,
        movieDao: MovieDao
    ) : ISearchRepository {
        return SearchRepositoryImpl(api, movieDao)
    }
}
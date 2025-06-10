package com.drygin.popcornplan.features.home.di

import com.drygin.popcornplan.common.data.local.AppDatabase
import com.drygin.popcornplan.common.data.local.dao.ImageDao
import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.local.dao.TrendingDao
import com.drygin.popcornplan.common.utils.TransactionRunner
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.repository.MovieRepositoryImpl
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionRunner(db: AppDatabase): TransactionRunner {
        return TransactionRunner(db)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApi,
        movieDao: MovieDao,
        imageDao: ImageDao,
        trendingMovieDao: TrendingDao,
        transactionRunner: TransactionRunner
    ) : IMovieRepository {
        return MovieRepositoryImpl(api, movieDao, imageDao, trendingMovieDao, transactionRunner)
    }
}
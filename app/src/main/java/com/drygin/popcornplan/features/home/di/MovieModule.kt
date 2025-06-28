package com.drygin.popcornplan.features.home.di

import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.repository.MovieRepositoryImpl
import com.drygin.popcornplan.features.home.domain.repository.IMovieRepository
import dagger.Binds
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
abstract class MovieModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(repoImpl: MovieRepositoryImpl) : IMovieRepository

    companion object {
        @Provides
        @Singleton
        fun provideMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }
    }
}
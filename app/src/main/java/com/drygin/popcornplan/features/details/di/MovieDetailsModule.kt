package com.drygin.popcornplan.features.details.di

import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.data.reposiroty.DetailsRepositoryImpl
import com.drygin.popcornplan.features.details.domain.repository.IDetailsRepository
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
object MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMovieDetailsApi(retrofit: Retrofit): MovieDetailsApi {
        return retrofit.create(MovieDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(
        api: MovieDetailsApi
    ) : IDetailsRepository {
        return DetailsRepositoryImpl(api)
    }
}
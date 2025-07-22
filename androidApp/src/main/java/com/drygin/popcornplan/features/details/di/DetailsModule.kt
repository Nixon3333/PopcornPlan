package com.drygin.popcornplan.features.details.di

import com.drygin.popcornplan.common.domain.details.repository.DetailsRepository
import com.drygin.popcornplan.common.domain.details.usecase.DetailsUseCases
import com.drygin.popcornplan.common.domain.details.usecase.ObserveMovieDetailsUseCase
import com.drygin.popcornplan.common.domain.details.usecase.RefreshMovieDetailsUseCase
import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.data.reposiroty.DetailsRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
val detailsModule = module {
    single<MovieDetailsApi> { get<Retrofit>().create(MovieDetailsApi::class.java) }
    single<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }

    single {
        DetailsUseCases(
            observeMovieDetails = ObserveMovieDetailsUseCase(get()),
            refreshMovieDetails = RefreshMovieDetailsUseCase(get())
        )
    }
}
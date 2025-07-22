package com.drygin.popcornplan.features.home.di

import com.drygin.popcornplan.common.domain.movie.repository.MovieRepository
import com.drygin.popcornplan.common.domain.movie.usecase.GetTopTrendingMoviesUseCase
import com.drygin.popcornplan.common.domain.movie.usecase.GetTrendingMoviesPagedUseCase
import com.drygin.popcornplan.common.domain.movie.usecase.MovieUseCases
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.repository.MovieRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
val movieModule = module {
    single<MovieApi> { get<Retrofit>().create(MovieApi::class.java) }

    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }

    single {
        MovieUseCases(
            getTrendingPaged = GetTrendingMoviesPagedUseCase(get()),
            getTopTrending = GetTopTrendingMoviesUseCase(get())
        )
    }
}
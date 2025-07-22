package com.drygin.popcornplan.features.search.di

import com.drygin.popcornplan.common.domain.search.usecase.ObserveMoviesByIdsUseCase
import com.drygin.popcornplan.common.domain.search.usecase.SearchMoviesUseCase
import com.drygin.popcornplan.common.domain.search.usecase.SearchUseCases
import com.drygin.popcornplan.features.search.data.api.SearchApi
import com.drygin.popcornplan.features.search.data.repository.SearchRepositoryImpl
import com.drygin.popcornplan.features.search.domain.repository.SearchRepository
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
val searchModule = module {
    single<SearchApi> { get<Retrofit>().create(SearchApi::class.java) }

    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get()) }

    single {
        SearchUseCases(
            searchMovies = SearchMoviesUseCase(get()),
            observeMoviesByIds = ObserveMoviesByIdsUseCase(get())
        )
    }
}
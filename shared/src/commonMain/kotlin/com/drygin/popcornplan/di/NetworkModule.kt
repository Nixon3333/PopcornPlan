package com.drygin.popcornplan.di

import com.drygin.popcornplan.features.details.data.remote.api.MovieDetailsApi
import com.drygin.popcornplan.features.search.data.remote.api.SearchApi
import com.drygin.popcornplan.features.sync.data.remote.api.FavoriteSyncApi
import com.drygin.popcornplan.features.sync.data.remote.api.FavoriteSyncApiImpl
import com.drygin.popcornplan.features.sync.data.remote.api.SyncApiImpl
import com.drygin.popcornplan.features.sync.data.repository.SyncRepositoryImpl
import com.drygin.popcornplan.features.sync.domain.remote.api.SyncApi
import com.drygin.popcornplan.features.sync.domain.repository.SyncRepository
import com.drygin.popcornplan.features.sync.domain.usecase.SyncUseCase
import com.drygin.popcornplan.features.trending.data.remote.api.MovieApi
import com.drygin.popcornplan.network.api.HttpClientProvider
import com.drygin.popcornplan.network.api.ServerHttpClientProvider
import com.drygin.popcornplan.network.api.TraktApiHttpClientProvider
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }

    single<HttpClientProvider>(named("trakt")) {
        TraktApiHttpClientProvider(get())
    }

    single<HttpClientProvider>(named("server")) {
        ServerHttpClientProvider(get())
    }

    single {
        val clientProvider = get<HttpClientProvider>(named("trakt"))
        val apiKey = get<String>(named("traktApiKey"))
        MovieDetailsApi(clientProvider, apiKey)
    }

    single {
        val clientProvider = get<HttpClientProvider>(named("trakt"))
        val apiKey = get<String>(named("traktApiKey"))
        MovieApi(clientProvider, apiKey)
    }

    single {
        val clientProvider = get<HttpClientProvider>(named("trakt"))
        val apiKey = get<String>(named("traktApiKey"))
        SearchApi(clientProvider, apiKey)
    }

    single<FavoriteSyncApi> {
        FavoriteSyncApiImpl(
            client = get<HttpClientProvider>(named("server")).getClient(),
            tokenRepository = get()
        )
    }

    single<SyncApi> {
        SyncApiImpl(
            client = get<HttpClientProvider>(named("server")).getClient(),
            tokenRepository = get()
        )
    }

    single { SyncUseCase(get()) }
    single<SyncRepository> { SyncRepositoryImpl(get(), get()) }
}
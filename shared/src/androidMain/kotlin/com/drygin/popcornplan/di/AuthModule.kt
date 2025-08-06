package com.drygin.popcornplan.di

import com.drygin.popcornplan.features.auth.data.remote.api.AuthApi
import com.drygin.popcornplan.features.auth.data.repository.AuthRepositoryImpl
import com.drygin.popcornplan.features.auth.domain.provider.TokenProvider
import com.drygin.popcornplan.features.auth.domain.repository.AuthRepository
import com.drygin.popcornplan.features.auth.domain.repository.TokenRepository
import com.drygin.popcornplan.features.auth.domain.usecase.LoginUseCase
import com.drygin.popcornplan.network.api.HttpClientProvider
import com.drygin.popcornplan.reatures.auth.data.provider.AndroidTokenProviderImpl
import com.drygin.popcornplan.reatures.auth.data.repository.AndroidTokenRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
val authModule = module {
    single { AuthApi(get<HttpClientProvider>(named("server")).getClient()) }

    single<TokenRepository> { AndroidTokenRepositoryImpl(androidContext()) }
    single<TokenProvider> { AndroidTokenProviderImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single(createdAtStart = true) { LoginUseCase(get()) }
}

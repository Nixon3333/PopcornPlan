package com.drygin.popcornplan.di

import com.drygin.popcornplan.data.remote.SyncWebSocketClient
import com.drygin.popcornplan.network.HttpClientProvider
import com.drygin.popcornplan.reatures.sync.SyncEventHandler
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
val websocketModule = module {
    single { SyncWebSocketClient(get<HttpClientProvider>(named("server")), get(), get()) }
    single { SyncEventHandler(get(), get()) }
}

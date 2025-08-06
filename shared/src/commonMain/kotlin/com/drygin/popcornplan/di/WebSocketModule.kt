package com.drygin.popcornplan.di

import com.drygin.popcornplan.network.api.HttpClientProvider
import com.drygin.popcornplan.network.websocket.SyncEventHandler
import com.drygin.popcornplan.network.websocket.WebSocketClient
import com.drygin.popcornplan.network.websocket.WebSocketStarter
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
val websocketModule = module {
    single { WebSocketClient(get<HttpClientProvider>(named("server")), get(), get()) }
    single { SyncEventHandler(get(), get()) }
    single(createdAtStart = true) {
        WebSocketStarter(get(), get())
    }
}
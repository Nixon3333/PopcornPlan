package com.drygin.popcornplan.di

import com.drygin.popcornplan.features.sync.SyncStarter
import com.drygin.popcornplan.features.sync.domain.SyncDataStore
import com.drygin.popcornplan.platform.AndroidSyncDataStore
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
val syncDataModule = module {
    single<SyncDataStore> { AndroidSyncDataStore(get(), get()) }
    single(createdAtStart = true) { SyncStarter(get(), get()) }
}
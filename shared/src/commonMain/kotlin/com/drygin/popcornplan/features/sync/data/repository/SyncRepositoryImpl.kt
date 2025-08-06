package com.drygin.popcornplan.features.sync.data.repository

import com.drygin.popcornplan.data.remote.safeApiCall
import com.drygin.popcornplan.domain.util.ApiResult
import com.drygin.popcornplan.features.sync.domain.SyncDataStore
import com.drygin.popcornplan.features.sync.domain.remote.api.SyncApi
import com.drygin.popcornplan.features.sync.domain.repository.SyncRepository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class SyncRepositoryImpl(
    private val syncApi: SyncApi,
    private val syncDataStore: SyncDataStore
) : SyncRepository {
    override suspend fun getInitialSync(): ApiResult<Unit> {
        return safeApiCall {
            val dto = syncApi.getInitialSync()
            println("QWEQWE syncResponseDto = ${dto.favorites.joinToString("::")}")
            syncDataStore.saveFavorites(dto.favorites)
            syncDataStore.saveReminders(dto.reminders)
        }
    }
}
package com.drygin.popcornplan.reatures.sync.data.repository

import com.drygin.popcornplan.features.sync.domain.remote.api.SyncApi
import com.drygin.popcornplan.features.sync.domain.repository.SyncRepository

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
class SyncRepositoryImpl(
    private val syncApi: SyncApi
) : SyncRepository {
    override suspend fun getInitialSync() {
        val syncResponseDto = syncApi.getInitialSync()
        println("QWEQWE syncResponseDto = ${syncResponseDto.favorites.joinToString("::")}")
    }
}
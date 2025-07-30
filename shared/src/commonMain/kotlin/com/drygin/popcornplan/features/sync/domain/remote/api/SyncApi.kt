package com.drygin.popcornplan.features.sync.domain.remote.api

import com.drygin.popcornplan.features.sync.data.remote.dto.SyncResponseDto

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface SyncApi {
    suspend fun getInitialSync(): SyncResponseDto
}
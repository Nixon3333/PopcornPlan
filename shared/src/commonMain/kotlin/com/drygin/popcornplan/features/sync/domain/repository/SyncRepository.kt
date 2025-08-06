package com.drygin.popcornplan.features.sync.domain.repository

import com.drygin.popcornplan.domain.util.ApiResult

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface SyncRepository {
    suspend fun getInitialSync(): ApiResult<Unit>
}
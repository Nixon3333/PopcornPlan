package com.drygin.popcornplan.features.sync.data.remote.api

import com.drygin.popcornplan.domain.util.ApiResult

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface FavoriteSyncApi {
    suspend fun syncInsertFavorite(traktId: Int): ApiResult<Unit>
    suspend fun syncDeleteFavorite(traktId: Int): ApiResult<Unit>
}
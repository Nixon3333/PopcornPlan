package com.drygin.popcornplan.features.favorite.data.remote.api

import com.drygin.popcornplan.features.favorite.data.remote.dto.FavoriteDto

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface FavoriteApi {
    suspend fun syncInsertFavorite(tmdbId: Int)
    suspend fun syncDeleteFavorite(tmdbId: Int)
}
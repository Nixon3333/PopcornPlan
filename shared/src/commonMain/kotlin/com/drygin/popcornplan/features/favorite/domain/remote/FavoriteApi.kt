package com.drygin.popcornplan.features.favorite.domain.remote

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface FavoriteApi {
    suspend fun syncInsertFavorite(traktId: Int)
    suspend fun syncDeleteFavorite(traktId: Int)
}
package com.drygin.popcornplan.features.sync.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
@Serializable
data class FavoriteDto(
    //val userId: String,
    val traktId: Int,
    //val title: String,
    //val type: String, // "movie" or "tv"
    //val posterUrl: String?,
    //val createdAt: Long
)
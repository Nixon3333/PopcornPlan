package com.drygin.popcornplan.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Serializable
data class IdsDto(
    val trakt: Int = 0,
    val slug: String? = null,
    val imdb: String? = null,
    val tmdb: Int = 0,
    val tvdb: Int = 0
)

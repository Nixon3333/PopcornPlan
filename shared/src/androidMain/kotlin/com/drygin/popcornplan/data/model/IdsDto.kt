package com.drygin.popcornplan.data.model

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
data class IdsDto(
    val trakt: Int = 0,
    val slug: String? = null,
    val imdb: String? = null,
    val tmdb: Int = 0,
    val tvdb: Int = 0
)

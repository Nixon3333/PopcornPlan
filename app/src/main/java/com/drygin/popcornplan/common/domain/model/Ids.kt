package com.drygin.popcornplan.common.domain.model

import androidx.compose.runtime.Immutable

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Immutable
data class Ids(
    val trakt: Int = 0,
    val slug: String? = "",
    val imdb: String? = "",
    val tmdb: Int = 0
)

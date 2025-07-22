package com.drygin.popcornplan.common.domain.search.model

import com.drygin.popcornplan.common.domain.movie.model.Movie

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
data class SearchItem(
    val type: String,
    val score: Double,
    val movie: Movie
)
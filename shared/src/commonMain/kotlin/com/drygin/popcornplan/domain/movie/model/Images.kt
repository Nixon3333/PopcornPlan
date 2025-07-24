package com.drygin.popcornplan.common.domain.movie.model

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
data class Images(
    val fanart: List<String> = listOf(),
    val poster: List<String> = listOf(),
    val logo: List<String> = listOf(),
    val clearart: List<String> = listOf(),
    val banner: List<String> = listOf(),
    val thumb: List<String> = listOf()
)
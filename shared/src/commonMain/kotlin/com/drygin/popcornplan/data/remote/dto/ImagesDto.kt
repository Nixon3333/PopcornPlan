package com.drygin.popcornplan.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Serializable
data class ImagesDto(
    val fanart: List<String> = emptyList(),
    val poster: List<String> = emptyList(),
    val logo: List<String> = emptyList(),
    val clearart: List<String> = emptyList(),
    val banner: List<String> = emptyList(),
    val thumb: List<String> = emptyList()
)

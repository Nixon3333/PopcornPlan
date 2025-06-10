package com.drygin.popcornplan.common.domain.model

import androidx.compose.runtime.Immutable

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Immutable
data class Images(
    val fanart: List<String> = emptyList(),
    val poster: List<String> = emptyList(),
    val logo: List<String> = emptyList(),
    val clearart: List<String> = emptyList(),
    val banner: List<String> = emptyList(),
    val thumb: List<String> = emptyList()
)
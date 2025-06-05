package com.drygin.popcornplan.common.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
data class MovieWithImages(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "traktId",
        entityColumn = "mediaTraktId"
    )
    val images: List<ImageEntity>
)

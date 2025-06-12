package com.drygin.popcornplan.common.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.drygin.popcornplan.common.data.local.entity.ImageEntity
import com.drygin.popcornplan.common.data.local.entity.MovieEntity

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

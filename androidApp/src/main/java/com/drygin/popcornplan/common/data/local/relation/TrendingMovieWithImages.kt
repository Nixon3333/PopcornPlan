package com.drygin.popcornplan.common.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.entity.TrendingMovieEntity

/**
 * Created by Drygin Nikita on 10.06.2025.
 */
data class TrendingMovieWithImages(
    @Embedded val trendingMovie: TrendingMovieEntity,
    @Relation(
        parentColumn = "traktId",
        entityColumn = "traktId",
        entity = MovieEntity::class
    )
    val movieWithImage: MovieWithImages
)

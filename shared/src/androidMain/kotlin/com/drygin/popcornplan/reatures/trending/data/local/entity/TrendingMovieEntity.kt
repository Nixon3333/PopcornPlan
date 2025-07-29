package com.drygin.popcornplan.reatures.trending.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.drygin.popcornplan.data.local.entity.MovieEntity

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Entity(
    tableName = "trending_movies",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["traktId"],
            childColumns = ["traktId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index(value = ["traktId"])]
)
data class TrendingMovieEntity(
    @PrimaryKey val traktId: Int,
    val watchers: Int,
    val pageIndex: Int
)
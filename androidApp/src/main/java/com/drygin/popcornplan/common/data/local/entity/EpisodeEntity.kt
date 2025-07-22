package com.drygin.popcornplan.common.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Entity(tableName = "episodes",
    foreignKeys = [
        ForeignKey(
            entity = SeasonEntity::class,
            parentColumns = ["traktId"],
            childColumns = ["seasonTraktId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("seasonTraktId")])
data class EpisodeEntity(
    @PrimaryKey val traktId: Int,
    val tvdb: Int?,
    val tmdb: Int?,
    val imdb: String = "",
    val season: Int,
    val number: Int,
    val title: String?,
    val seasonTraktId: Int
)

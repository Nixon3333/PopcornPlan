package com.drygin.popcornplan.common.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Entity(tableName = "shows")
data class ShowEntity(
    @PrimaryKey val traktId: Int,
    val slug: String = "",
    val tvdb: Int?,
    val imdb: String = "",
    val tmdb: Int?,
    val title: String,
    val year: Int?
)

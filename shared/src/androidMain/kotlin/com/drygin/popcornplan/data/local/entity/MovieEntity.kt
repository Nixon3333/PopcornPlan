package com.drygin.popcornplan.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 29.05.2025.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val traktId: Int,
    val slug: String = "",
    val imdb: String = "",
    val tmdb: Int? = null,
    val title: String,
    val year: Int,
    val overview: String,
    val releaseDate: String,
    val rating: Double
)
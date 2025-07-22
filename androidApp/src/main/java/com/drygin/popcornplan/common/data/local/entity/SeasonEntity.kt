package com.drygin.popcornplan.common.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Entity(tableName = "seasons")
data class SeasonEntity(
    @PrimaryKey val traktId: Int,
    val number: Int?,
    val tvdb: Int?,
    val tmdb: Int?
)

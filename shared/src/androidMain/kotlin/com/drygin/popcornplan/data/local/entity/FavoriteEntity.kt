package com.drygin.popcornplan.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.PrimaryKey

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
@Entity(
    tableName = "favorite",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["traktId"],
            childColumns = ["traktId"],
            onDelete = NO_ACTION
        )
    ]
)
data class FavoriteEntity(
    @PrimaryKey
    val traktId: Int
)

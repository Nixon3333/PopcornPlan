package com.drygin.popcornplan.data.local.entity

import androidx.room.Entity
import com.drygin.popcornplan.common.domain.movie.model.ImageType

/**
 * Created by Drygin Nikita on 05.06.2025.
 */
@Entity(
    tableName = "images",
    primaryKeys = ["mediaTraktId", "type"]
)
data class ImageEntity(
    val mediaTraktId: Int,
    val type: String, // fanart, poster, etc
    val url: String
) {
    fun getImageType(): ImageType? = ImageType.from(type)
}

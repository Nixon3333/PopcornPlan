package com.drygin.popcornplan.data.mapper.entity

import com.drygin.popcornplan.common.domain.movie.model.ImageType
import com.drygin.popcornplan.common.domain.movie.model.Images
import com.drygin.popcornplan.data.local.entity.ImageEntity

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun List<ImageEntity>.toDomain(): Images {
    val fanart = mutableListOf<String>()
    val poster = mutableListOf<String>()
    val logo = mutableListOf<String>()
    val clearart = mutableListOf<String>()
    val banner = mutableListOf<String>()
    val thumb = mutableListOf<String>()

    forEach { entity ->
        when (entity.getImageType()) {
            ImageType.FANART -> fanart.add(entity.url)
            ImageType.POSTER -> poster.add(entity.url)
            ImageType.LOGO -> logo.add(entity.url)
            ImageType.CLEARART -> clearart.add(entity.url)
            ImageType.BANNER -> banner.add(entity.url)
            ImageType.THUMB -> thumb.add(entity.url)
            null -> { /* ignore */ }
        }
    }

    return Images(
        fanart = fanart,
        poster = poster,
        logo = logo,
        clearart = clearart,
        banner = banner,
        thumb = thumb
    )
}
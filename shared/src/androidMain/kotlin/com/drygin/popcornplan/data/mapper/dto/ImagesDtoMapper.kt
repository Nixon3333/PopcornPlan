package com.drygin.popcornplan.data.mapper.dto

import com.drygin.popcornplan.common.domain.movie.model.Images
import com.drygin.popcornplan.data.remote.dto.ImagesDto

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun ImagesDto.toDomain(): Images = Images(
    fanart = fanart,
    poster = poster,
    logo = logo,
    clearart = clearart,
    banner = banner,
    thumb = thumb
)
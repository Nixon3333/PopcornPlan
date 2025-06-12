package com.drygin.popcornplan.common.data.mapper.dto

import com.drygin.popcornplan.common.data.model.ImagesDto
import com.drygin.popcornplan.common.domain.model.Images

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
package com.drygin.popcornplan.reatures.sync.data.mapper

import com.drygin.popcornplan.data.local.entity.FavoriteEntity
import com.drygin.popcornplan.features.sync.data.remote.dto.FavoriteDto

/**
 * Created by Drygin Nikita on 02.08.2025.
 */
fun FavoriteDto.toEntity() = FavoriteEntity(
    this.traktId
)
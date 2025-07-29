package com.drygin.popcornplan.data.mapper.dto

import com.drygin.popcornplan.common.domain.movie.model.Ids
import com.drygin.popcornplan.data.remote.dto.IdsDto

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun IdsDto.toDomain(): Ids = Ids(
    trakt = trakt,
    slug = slug ?: "",
    imdb = imdb ?: "",
    tmdb = tmdb
)
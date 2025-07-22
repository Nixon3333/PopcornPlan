package com.drygin.popcornplan.common.data.mapper.dto

import com.drygin.popcornplan.common.data.model.IdsDto
import com.drygin.popcornplan.common.domain.movie.model.Ids

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun IdsDto.toDomain(): Ids = Ids(
    trakt = trakt,
    slug = slug ?: "",
    imdb = imdb ?: "",
    tmdb = tmdb
)
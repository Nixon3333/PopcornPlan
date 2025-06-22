package com.drygin.popcornplan.common.data.mapper.entity

import com.drygin.popcornplan.common.data.local.entity.ImageEntity
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.relation.MovieWithImages
import com.drygin.popcornplan.common.domain.model.Ids
import com.drygin.popcornplan.common.domain.model.Movie

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun MovieEntity.toDomain(images: List<ImageEntity>): Movie = Movie(
    ids = Ids(traktId),
    title = title,
    year = year,
    isFavorite = favorite,
    overview = overview,
    images = images.toDomain()
)

fun MovieEntity.toDomain(): Movie = Movie(
    ids = Ids(traktId),
    title = title,
    year = year,
    isFavorite = favorite,
    overview = overview
)

fun MovieWithImages.toDomain(): Movie = movieEntity.toDomain(images)
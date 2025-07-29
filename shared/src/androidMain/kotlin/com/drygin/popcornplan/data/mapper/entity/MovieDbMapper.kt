package com.drygin.popcornplan.data.mapper.entity

import com.drygin.popcornplan.common.domain.movie.model.ImageType
import com.drygin.popcornplan.data.local.entity.ImageEntity
import com.drygin.popcornplan.data.local.entity.MovieEntity
import com.drygin.popcornplan.data.remote.dto.ImagesDto
import com.drygin.popcornplan.data.remote.dto.MovieDto

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    traktId = ids.trakt,
    slug = ids.slug ?: "",
    imdb = ids.imdb ?: "",
    tmdb = ids.tmdb,
    title = title ?: "",
    year = year ?: 1970,
    overview = overview ?: "",
    releaseDate = released ?: "",
    rating = rating
)

fun ImagesDto.toEntities(traktId: Int): List<ImageEntity> {
    val entities = mutableListOf<ImageEntity>()

    fanart.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.FANART.typeName, url = url)
    }
    poster.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.POSTER.typeName, url = url)
    }
    logo.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.LOGO.typeName, url = url)
    }
    clearart.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.CLEARART.typeName, url = url)
    }
    banner.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.BANNER.typeName, url = url)
    }
    thumb.forEach { url ->
        entities += ImageEntity(mediaTraktId = traktId, type = ImageType.THUMB.typeName, url = url)
    }

    return entities
}
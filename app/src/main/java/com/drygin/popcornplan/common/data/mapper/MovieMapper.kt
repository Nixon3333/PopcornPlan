package com.drygin.popcornplan.common.data.mapper

import com.drygin.popcornplan.common.data.local.entity.ImageEntity
import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.local.entity.MovieWithImages
import com.drygin.popcornplan.common.data.model.IdsDto
import com.drygin.popcornplan.common.data.model.ImagesDto
import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.common.domain.model.Ids
import com.drygin.popcornplan.common.domain.model.ImageType
import com.drygin.popcornplan.common.domain.model.Images
import com.drygin.popcornplan.common.domain.model.Movie

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
fun MovieDto.toDomain(): Movie =
    Movie(
        title = title,
        year = year,
        ids = ids.toDomain(),
        tagline = tagline,
        overview = overview,
        released = released,
        runtime = runtime,
        country = country,
        trailer = trailer,
        homepage = homepage,
        status = status,
        rating = rating,
        votes = votes,
        commentCount = comment_count,
        updatedAt = updated_at,
        language = language,
        languages = languages,
        availableTranslations = available_translations,
        genres = genres,
        certification = certification,
        originalTitle = original_title,
        afterCredits = after_credits,
        duringCredits = during_credits,
        images = images.toDomain()
    )

fun MovieEntity.toDomain(images: List<ImageEntity>): Movie =
    Movie(
        ids = Ids(traktId),
        title = title,
        year = year,
        watchers = watchers,
        isFavorite = favorite,
        images = images.toDomain()
    )

fun MovieWithImages.toDomain(): Movie {
    return movieEntity.toDomain(images).copy(
        images = images.toDomain()
    )
}

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
            null -> { /* Игнорируем неизвестные типы */ }
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


fun MovieDto.toEntity() = MovieEntity(
    traktId = this.ids.trakt,
    slug = this.ids.slug,
    imdb = this.ids.imdb,
    tmdb = this.ids.tmdb,
    title = this.title,
    year = this.year ?: 1970,
    overview = this.overview,
    releaseDate = this.released,
    rating = this.rating,
    watchers = this.watchers
)

fun IdsDto.toDomain(): Ids =
    Ids(
        trakt = trakt,
        slug = slug,
        imdb = imdb,
        tmdb = tmdb
    )

fun ImagesDto.toDomain(): Images =
    Images(
        fanart = fanart,
        poster = poster,
        logo = logo,
        clearart = clearart,
        banner = banner,
        thumb = thumb
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
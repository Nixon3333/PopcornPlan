package com.drygin.popcornplan.common.data.mapper

import com.drygin.popcornplan.common.data.local.entity.MovieEntity
import com.drygin.popcornplan.common.data.model.IdsDto
import com.drygin.popcornplan.common.data.model.ImagesDto
import com.drygin.popcornplan.common.data.model.MovieDto
import com.drygin.popcornplan.common.domain.model.Ids
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

fun MovieEntity.toDomain(): Movie =
    Movie(
        ids = Ids(id),
        title = title,
        year = year,
        watchers = watchers,
        isFavorite = favorite,
        images = Images(poster = listOf(posterUrl))
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
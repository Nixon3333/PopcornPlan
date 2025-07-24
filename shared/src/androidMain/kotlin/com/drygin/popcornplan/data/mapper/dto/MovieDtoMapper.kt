package com.drygin.popcornplan.data.mapper.dto

import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.data.model.MovieDto

/**
 * Created by Drygin Nikita on 11.06.2025.
 */
fun MovieDto.toDomain(): Movie = Movie(
    title = title ?: "",
    year = year ?: 1970,
    ids = ids.toDomain(),
    tagline = tagline ?: "",
    overview = overview ?: "",
    released = released ?: "",
    runtime = runtime,
    country = country ?: "",
    trailer = trailer,
    homepage = homepage,
    status = status ?: "",
    rating = rating,
    votes = votes,
    commentCount = comment_count,
    updatedAt = updated_at ?: "",
    language = language ?: "",
    languages = languages,
    availableTranslations = available_translations,
    genres = genres,
    certification = certification ?: "",
    originalTitle = original_title ?: "",
    afterCredits = after_credits,
    duringCredits = during_credits,
    images = images.toDomain()
)
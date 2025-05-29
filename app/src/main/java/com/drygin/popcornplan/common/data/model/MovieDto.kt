package com.drygin.popcornplan.common.data.model

/**
 * Created by Drygin Nikita on 24.05.2025.
 */

data class MovieDto(
    val title: String?,
    val year: Int?,
    val ids: IdsDto,
    val tagline: String?,
    val overview: String?,
    val released: String?,
    val runtime: Int?,
    val country: String?,
    val trailer: String?,
    val homepage: String?,
    val status: String?,
    val rating: Double?,
    val votes: Int?,
    val comment_count: Int?,
    val updated_at: String?,
    val language: String?,
    val languages: List<String>?,
    val available_translations: List<String>?,
    val genres: List<String>?,
    val certification: String?,
    val original_title: String?,
    val after_credits: Boolean?,
    val during_credits: Boolean?,
    val images: ImagesDto?
)

data class IdsDto(
    val trakt: Int?,
    val slug: String?,
    val imdb: String?,
    val tmdb: Int?
)

data class ImagesDto(
    val fanart: List<String>,
    val poster: List<String>,
    val logo: List<String>,
    val clearart: List<String>,
    val banner: List<String>,
    val thumb: List<String>
)

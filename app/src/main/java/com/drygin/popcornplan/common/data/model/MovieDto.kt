package com.drygin.popcornplan.common.data.model

/**
 * Created by Drygin Nikita on 24.05.2025.
 */

data class MovieDto(
    val title: String = "",
    val year: Int? = 1970,
    val ids: IdsDto = IdsDto(),
    val tagline: String = "",
    val watchers: Int = 0,
    val overview: String = "",
    val released: String = "",
    val runtime: Int = 0,
    val country: String = "",
    val trailer: String = "",
    val homepage: String? = "",
    val status: String = "",
    val rating: Double = 0.0,
    val votes: Int = 0,
    val comment_count: Int = 0,
    val updated_at: String = "",
    val language: String = "",
    val languages: List<String> = emptyList(),
    val available_translations: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val certification: String = "",
    val original_title: String = "",
    val after_credits: Boolean = false,
    val during_credits: Boolean = false,
    val images: ImagesDto = ImagesDto()
)

data class IdsDto(
    val trakt: Int = 0,
    val slug: String = "",
    val imdb: String? = "",
    val tmdb: Int = 0
)

data class ImagesDto(
    val fanart: List<String> = emptyList(),
    val poster: List<String> = emptyList(),
    val logo: List<String> = emptyList(),
    val clearart: List<String> = emptyList(),
    val banner: List<String> = emptyList(),
    val thumb: List<String> = emptyList()
)

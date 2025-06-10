package com.drygin.popcornplan.common.data.model

/**
 * Created by Drygin Nikita on 24.05.2025.
 */

data class MovieDto(
    val title: String? = null,
    val year: Int? = 1970,
    val ids: IdsDto = IdsDto(),
    val tagline: String? = null,
    val watchers: Int = 0,
    val overview: String? = null,
    val released: String? = null,
    val runtime: Int = 0,
    val country: String = "",
    val trailer: String? = null,
    val homepage: String? = null,
    val status: String? = null,
    val rating: Double = 0.0,
    val votes: Int = 0,
    val comment_count: Int = 0,
    val updated_at: String? = null,
    val language: String? = null,
    val languages: List<String> = emptyList(),
    val available_translations: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val certification: String? = null,
    val original_title: String? = null,
    val after_credits: Boolean = false,
    val during_credits: Boolean = false,
    val images: ImagesDto = ImagesDto()
)

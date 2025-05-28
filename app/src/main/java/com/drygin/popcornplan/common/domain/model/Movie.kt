package com.drygin.popcornplan.common.domain.model

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
data class Movie(
    val title: String?,
    val year: Int?,
    val ids: Ids,
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
    val commentCount: Int?,
    val updatedAt: String?,
    val language: String?,
    val languages: List<String>?,
    val availableTranslations: List<String>?,
    val genres: List<String>?,
    val certification: String?,
    val originalTitle: String?,
    val afterCredits: Boolean?,
    val duringCredits: Boolean?,
    val images: Images
)

data class Ids(
    val trakt: Int?,
    val slug: String?,
    val imdb: String?,
    val tmdb: Int?
)

data class Images(
    val fanart: List<String>,
    val poster: List<String>,
    val logo: List<String>,
    val clearart: List<String>,
    val banner: List<String>,
    val thumb: List<String>
)

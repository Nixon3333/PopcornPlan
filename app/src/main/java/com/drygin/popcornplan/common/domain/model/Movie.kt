package com.drygin.popcornplan.common.domain.model

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
data class Movie(
    val title: String = "No title",
    val year: Int? = 1970,
    val ids: Ids = Ids(),
    val tagline: String = "",
    val watchers: Int = 0,
    val isFavorite: Boolean = false,
    val overview: String = "",
    val released: String = "",
    val runtime: Int = 0,
    val country: String = "",
    val trailer: String = "",
    val homepage: String? = "",
    val status: String = "",
    val rating: Double = 0.0,
    val votes: Int = 0,
    val commentCount: Int = 0,
    val updatedAt: String = "",
    val language: String = "",
    val languages: List<String> = emptyList(),
    val availableTranslations: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val certification: String = "",
    val originalTitle: String = "",
    val afterCredits: Boolean = false,
    val duringCredits: Boolean = false,
    val images: Images = Images()
)

data class Ids(
    val trakt: Int = 0,
    val slug: String = "",
    val imdb: String? = "",
    val tmdb: Int = 0
)

data class Images(
    val fanart: List<String> = emptyList(),
    val poster: List<String> = emptyList(),
    val logo: List<String> = emptyList(),
    val clearart: List<String> = emptyList(),
    val banner: List<String> = emptyList(),
    val thumb: List<String> = emptyList()
)

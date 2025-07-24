package com.drygin.popcornplan.common.domain.movie.model

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
data class Movie(
    val title: String = "No title",
    val year: Int = 1970,
    val ids: Ids = Ids(),
    val tagline: String = "",
    val watchers: Int = 0,
    val isFavorite: Boolean = false,
    val overview: String = "",
    val released: String = "",
    val runtime: Int = 0,
    val country: String = "",
    val trailer: String? = "",
    val homepage: String? = "",
    val status: String = "",
    val rating: Double = 0.0,
    val votes: Int = 0,
    val commentCount: Int = 0,
    val updatedAt: String = "",
    val language: String = "",
    val languages: List<String> = listOf(),
    val availableTranslations: List<String> = listOf(),
    val genres: List<String> = listOf(),
    val certification: String = "",
    val originalTitle: String = "",
    val afterCredits: Boolean = false,
    val duringCredits: Boolean = false,
    val images: Images = Images()
)

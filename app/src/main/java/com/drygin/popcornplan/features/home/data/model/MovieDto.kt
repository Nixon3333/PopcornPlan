package com.drygin.popcornplan.features.home.data.model

/**
 * Created by Drygin Nikita on 24.05.2025.
 */
// TODO: Уточнить поля на сервере
data class MovieDto(
    val id: Int,
    val title: String,
    val description: String,
    val year: Int,
    val posterUrl: String
)

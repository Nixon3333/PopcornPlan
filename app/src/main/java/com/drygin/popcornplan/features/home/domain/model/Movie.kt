package com.drygin.popcornplan.features.home.domain.model

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val year: Int,
    val posterUrl: String
)

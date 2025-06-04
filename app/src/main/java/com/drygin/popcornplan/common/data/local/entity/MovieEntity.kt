package com.drygin.popcornplan.common.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drygin.popcornplan.common.data.model.MovieDto

/**
 * Created by Drygin Nikita on 29.05.2025.
 */
// TODO: Добавить тип контента
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val year: Int,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String,
    val rating: Double,
    val watchers: Int,
    val favorite: Boolean = false
) {
    companion object {
        fun fromDto(dto: MovieDto) = MovieEntity(
            id = dto.ids.trakt,
            title = dto.title,
            year = dto.year ?: 1970,
            overview = dto.overview,
            posterUrl = dto.images.poster.firstOrNull() ?: "",
            releaseDate = dto.released,
            rating = dto.rating,
            watchers = dto.watchers
        )

        /*fun fromDomain(movie: Movie) = MovieEntity(
            id = movie.ids.trakt,
            title = movie.title,
            year = movie.year,
            overview = movie.overview,
            posterUrl = movie.images.poster.firstOrNull() ?: "",
            releaseDate = movie.released,
            rating = movie.rating,
            watchers = movie.watchers
        )*/
    }
}

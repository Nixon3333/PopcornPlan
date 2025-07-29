package com.drygin.popcornplan.data.local.utils

import com.drygin.popcornplan.data.local.dao.MovieDao
import com.drygin.popcornplan.data.local.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Drygin Nikita on 17.06.2025.
 */
suspend fun MovieDao.saveMoviesPreservingFavorites(newMovies: List<MovieEntity>) {
    withContext(Dispatchers.IO) {
        val ids = newMovies.map { it.traktId }
        val existingMovies = getMoviesByIds(ids)
        val existingMoviesMap = existingMovies.associateBy { it.movieEntity.traktId }
        val merged = newMovies.map { newMovie ->
            val oldMovie = existingMoviesMap[newMovie.traktId]
            newMovie.copy(favorite = oldMovie?.movieEntity?.favorite == true)
        }
        merged.forEach { upsert(it) }
    }
}
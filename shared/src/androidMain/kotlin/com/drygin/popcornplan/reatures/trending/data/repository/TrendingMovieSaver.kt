package com.drygin.popcornplan.reatures.trending.data.repository

import androidx.room.withTransaction
import com.drygin.popcornplan.data.local.AppDatabase
import com.drygin.popcornplan.data.local.utils.saveMoviesPreservingFavorites
import com.drygin.popcornplan.data.mapper.entity.toEntities
import com.drygin.popcornplan.data.mapper.entity.toEntity
import com.drygin.popcornplan.features.trending.data.remote.dto.TrendingMovieDto
import com.drygin.popcornplan.reatures.trending.data.mapper.toDomain
import com.drygin.popcornplan.reatures.trending.data.mapper.toEntity
import com.drygin.popcornplan.reatures.trending.data.mapper.toMovieDto

/**
 * Created by Drygin Nikita on 30.06.2025.
 */
class TrendingMovieSaver(
    private val database: AppDatabase
) {
    suspend fun saveTrendingMovies(
        trendingMoviesDto: List<TrendingMovieDto>,
        clearBeforeInsert: Boolean = true
    ) = database.withTransaction {
        val movieDao = database.movieDao()
        val imageDao = database.imageDao()
        val trendingDao = database.trendingDao()

        if (clearBeforeInsert) {
            trendingDao.clearAll()
        }

        val moviesDto = trendingMoviesDto.map { it.toMovieDto() }
        val trendingMovies = trendingMoviesDto.map { it.toDomain() }


        val entities = moviesDto.map { it.toEntity() }
        movieDao.saveMoviesPreservingFavorites(entities)

        trendingDao.insertAll(trendingMovies.map { it.toEntity() })

        trendingMoviesDto.forEach { movieDto ->
            val imageEntities = movieDto.movie.images.toEntities(movieDto.movie.ids.trakt)
            imageDao.insertAll(imageEntities)
        }

        return@withTransaction trendingDao.getTrendingMovies().map { movieWithImages ->
            movieWithImages.toDomain()
        }
    }
}
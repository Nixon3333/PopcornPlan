package com.drygin.popcornplan.common.data.repository

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.mapper.entity.toDomain
import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
class FavoriteRepositoryImpl(
    private val movieDao: MovieDao,
) : FavoriteRepository {
    override suspend fun getFavoriteMovies(): Flow<List<Movie>> =
        movieDao.movies()
            .map { entities ->
                entities
                    .filter { it.movieEntity.favorite }
                    .map { it.toDomain() }
            }

    override suspend fun onToggleFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            val localMovie = movieDao.getMovie(movieId)
            localMovie?.let {
                movieDao.upsert(it.copy(favorite = !it.favorite))
            }
        }
    }
}
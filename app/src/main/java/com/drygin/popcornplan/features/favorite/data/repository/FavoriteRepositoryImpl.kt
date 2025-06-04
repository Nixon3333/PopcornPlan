package com.drygin.popcornplan.features.favorite.data.repository

import com.drygin.popcornplan.common.data.local.dao.MovieDao
import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.common.domain.model.Movie
import com.drygin.popcornplan.features.favorite.domain.repository.IFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
class FavoriteRepositoryImpl @Inject constructor(
    val dao: MovieDao
) : IFavoriteRepository {
    override suspend fun getFavoriteMovies(): Flow<List<Movie>> =
        dao.getMovies()
            .map { entities ->
                entities
                    .filter { it.favorite }
                    .map { it.toDomain() }
            }

    override suspend fun removeFavorite(movieId: Int) {
        withContext(Dispatchers.IO) {
            dao.getMovie(movieId)?.let {
                dao.insertAll(listOf(it.copy(favorite = false)))
            }
        }
    }
}
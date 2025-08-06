package com.drygin.popcornplan.reatures.favorite.data.repository

import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.data.local.dao.FavoriteDao
import com.drygin.popcornplan.data.local.dao.MovieDao
import com.drygin.popcornplan.data.mapper.entity.toDomain
import com.drygin.popcornplan.data.mapper.entity.toEntity
import com.drygin.popcornplan.data.remote.safeApiCall
import com.drygin.popcornplan.features.sync.data.remote.api.FavoriteSyncApi
import com.drygin.popcornplan.features.trending.data.remote.api.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
class FavoriteRepositoryImpl(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi,
    private val favoriteDao: FavoriteDao,
    private val favoriteSyncApi: FavoriteSyncApi
) : FavoriteRepository {
    override suspend fun getFavoriteMovies(): Flow<List<Movie>> {
        return combine(
            movieDao.movies(),
            favoriteDao.getAllIds()
        ) { movies, favoritesIds ->
            movies.filter { (movieEntity, _) ->
                movieEntity.traktId in favoritesIds
            }
                .map { movieWithImages ->
                    movieWithImages.toDomain()
                }
        }
    }

    override suspend fun onToggleFavorite(traktId: Int) {
        if (!favoriteDao.exists(traktId)) {
            favoriteDao.insertById(traktId)
            safeApiCall { favoriteSyncApi.syncInsertFavorite(traktId) }
        } else {
            favoriteDao.deleteById(traktId)
            safeApiCall { favoriteSyncApi.syncDeleteFavorite(traktId) }
        }
    }


    override suspend fun setFavorite(traktId: Int, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            val localMovie = movieDao.getMovie(traktId)

            if (isFavorite) {
                val movie = localMovie ?: movieApi.getMovie(traktId)?.toEntity()?.also {
                    movieDao.insertAll(listOf(it))
                }

                if (movie != null) {
                    favoriteDao.insertById(traktId)
                }
            } else {
                favoriteDao.deleteById(traktId)
            }
        }
}
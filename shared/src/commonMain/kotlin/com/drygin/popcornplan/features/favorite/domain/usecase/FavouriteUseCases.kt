package com.drygin.popcornplan.common.domain.favorite.usecase

import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import com.drygin.popcornplan.features.sync.data.remote.api.FavoriteSyncApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class FavouriteUseCases(
    val toggleFavorite: ToggleFavoriteMovieUseCase,
    val getFavourite: GetFavoriteMoviesUseCase,
    val syncInsertFavorite: SyncInsertFavoriteUseCase,
    val syncDeleteFavorite: SyncDeleteFavoriteUseCase,
    val existUseCase: ExistUseCase
)

class ToggleFavoriteMovieUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.onToggleFavorite(movieId)
}

class GetFavoriteMoviesUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> = repository.getFavoriteMovies()
}

class SyncInsertFavoriteUseCase(
    private val favoriteSyncApi: FavoriteSyncApi
) {
    suspend operator fun invoke(traktId: Int) = favoriteSyncApi.syncInsertFavorite(traktId)
}

class SyncDeleteFavoriteUseCase(
    private val favoriteSyncApi: FavoriteSyncApi
) {
    suspend operator fun invoke(traktId: Int) = favoriteSyncApi.syncDeleteFavorite(traktId)
}

class ExistUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(traktId: Int): Flow<Boolean> {
        return repository.getFavoriteMovies()
            .map { favorites -> favorites.any { it.ids.trakt == traktId } }
    }
}


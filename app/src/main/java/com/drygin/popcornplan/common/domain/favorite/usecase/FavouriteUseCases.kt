package com.drygin.popcornplan.common.domain.favorite.usecase

import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
data class FavouriteUseCases(
    val toggleFavorite: ToggleFavoriteMovieUseCase,
    val getFavourite: GetFavoriteMoviesUseCase
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


package com.drygin.popcornplan.features.favorite.di

import com.drygin.popcornplan.common.data.repository.FavoriteRepositoryImpl
import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.favorite.usecase.GetFavoriteMoviesUseCase
import com.drygin.popcornplan.common.domain.favorite.usecase.ToggleFavoriteMovieUseCase
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 02.06.2025.
 */
val favoriteModule = module {
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }

    single {
        FavouriteUseCases(
            toggleFavorite = ToggleFavoriteMovieUseCase(get()),
            getFavourite = GetFavoriteMoviesUseCase(get())
        )
    }
}

package com.drygin.popcornplan.di

import com.drygin.popcornplan.common.domain.details.repository.DetailsRepository
import com.drygin.popcornplan.common.domain.details.usecase.DetailsUseCases
import com.drygin.popcornplan.common.domain.details.usecase.ObserveMovieDetailsUseCase
import com.drygin.popcornplan.common.domain.details.usecase.RefreshMovieDetailsUseCase
import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.favorite.usecase.ExistUseCase
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.favorite.usecase.GetFavoriteMoviesUseCase
import com.drygin.popcornplan.common.domain.favorite.usecase.SyncDeleteFavoriteUseCase
import com.drygin.popcornplan.common.domain.favorite.usecase.SyncInsertFavoriteUseCase
import com.drygin.popcornplan.common.domain.favorite.usecase.ToggleFavoriteMovieUseCase
import com.drygin.popcornplan.common.domain.movie.usecase.GetTopTrendingMoviesUseCase
import com.drygin.popcornplan.common.domain.movie.usecase.MovieUseCases
import com.drygin.popcornplan.common.domain.reminder.repository.ReminderRepository
import com.drygin.popcornplan.common.domain.reminder.usecase.AddReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.CancelReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.GetRemindersUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.ReminderUseCases
import com.drygin.popcornplan.common.domain.reminder.usecase.RemoveReminderUseCase
import com.drygin.popcornplan.common.domain.reminder.usecase.ScheduleReminderUseCase
import com.drygin.popcornplan.common.domain.search.usecase.ObserveMoviesByIdsUseCase
import com.drygin.popcornplan.common.domain.search.usecase.SearchMoviesUseCase
import com.drygin.popcornplan.common.domain.search.usecase.SearchUseCases
import com.drygin.popcornplan.data.repository.MovieRepositoryImpl
import com.drygin.popcornplan.domain.movie.repository.CommonMovieRepository
import com.drygin.popcornplan.features.search.domain.repository.SearchRepository
import com.drygin.popcornplan.reatures.details.data.repository.DetailsRepositoryImpl
import com.drygin.popcornplan.reatures.favorite.data.repository.FavoriteRepositoryImpl
import com.drygin.popcornplan.reatures.reminder.data.repository.ReminderRepositoryImpl
import com.drygin.popcornplan.reatures.search.data.repository.SearchRepositoryImpl
import com.drygin.popcornplan.reatures.trending.data.repository.TrendingMovieSaver
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 24.07.2025.
 */
val featureModule = module {
    // Details
    single<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
    single {
        DetailsUseCases(
            observeMovieDetails = ObserveMovieDetailsUseCase(get()),
            refreshMovieDetails = RefreshMovieDetailsUseCase(get())
        )
    }

    // Favorite
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            movieDao = get(),
            movieApi = get(),
            favoriteDao = get(),
            favoriteSyncApi = get()
        )
    }
    single {
        FavouriteUseCases(
            toggleFavorite = ToggleFavoriteMovieUseCase(get()),
            getFavourite = GetFavoriteMoviesUseCase(get()),
            syncInsertFavorite = SyncInsertFavoriteUseCase(get()),
            syncDeleteFavorite = SyncDeleteFavoriteUseCase(get()),
            existUseCase = ExistUseCase(get())
        )
    }

    // Movies
    single<TrendingMovieSaver> { TrendingMovieSaver(get()) }
    single<CommonMovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
    single {
        MovieUseCases(
            getTopTrending = GetTopTrendingMoviesUseCase(get())
        )
    }

    // Reminder
    single<ReminderRepository> { ReminderRepositoryImpl(get()) }
    single {
        ReminderUseCases(
            getReminders = GetRemindersUseCase(get()),
            addReminder = AddReminderUseCase(get()),
            removeReminder = RemoveReminderUseCase(get()),
            scheduleReminder = ScheduleReminderUseCase(get()),
            cancelReminder = CancelReminderUseCase(get())
        )
    }

    // Search
    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get()) }
    single {
        SearchUseCases(
            searchMovies = SearchMoviesUseCase(get()),
            observeMoviesByIds = ObserveMoviesByIdsUseCase(get())
        )
    }
}

package com.drygin.popcornplan.di

import com.drygin.popcornplan.common.domain.details.repository.DetailsRepository
import com.drygin.popcornplan.common.domain.details.usecase.DetailsUseCases
import com.drygin.popcornplan.common.domain.details.usecase.ObserveMovieDetailsUseCase
import com.drygin.popcornplan.common.domain.details.usecase.RefreshMovieDetailsUseCase
import com.drygin.popcornplan.common.domain.favorite.repository.FavoriteRepository
import com.drygin.popcornplan.common.domain.favorite.usecase.FavouriteUseCases
import com.drygin.popcornplan.common.domain.favorite.usecase.GetFavoriteMoviesUseCase
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
import com.drygin.popcornplan.domain.repository.MovieRepository
import com.drygin.popcornplan.domain.search.repository.SearchRepository
import com.drygin.popcornplan.features.details.data.api.MovieDetailsApi
import com.drygin.popcornplan.features.details.data.reposiroty.DetailsRepositoryImpl
import com.drygin.popcornplan.features.favorite.repository.FavoriteRepositoryImpl
import com.drygin.popcornplan.features.home.data.api.MovieApi
import com.drygin.popcornplan.features.home.data.repository.MovieRepositoryImpl
import com.drygin.popcornplan.features.reminder.data.repository.ReminderRepositoryImpl
import com.drygin.popcornplan.features.search.data.api.SearchApi
import com.drygin.popcornplan.features.search.data.repository.SearchRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by Drygin Nikita on 24.07.2025.
 */
val featureModule = module {
    // Details
    single<MovieDetailsApi> { get<Retrofit>().create(MovieDetailsApi::class.java) }
    single<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
    single {
        DetailsUseCases(
            observeMovieDetails = ObserveMovieDetailsUseCase(get()),
            refreshMovieDetails = RefreshMovieDetailsUseCase(get())
        )
    }

    // Favorite
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single {
        FavouriteUseCases(
            toggleFavorite = ToggleFavoriteMovieUseCase(get()),
            getFavourite = GetFavoriteMoviesUseCase(get())
        )
    }

    // Movies
    single<MovieApi> { get<Retrofit>().create(MovieApi::class.java) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
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
    single<SearchApi> { get<Retrofit>().create(SearchApi::class.java) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get()) }
    single {
        SearchUseCases(
            searchMovies = SearchMoviesUseCase(get()),
            observeMoviesByIds = ObserveMoviesByIdsUseCase(get())
        )
    }
}

package com.drygin.popcornplan.common.di

import androidx.lifecycle.SavedStateHandle
import com.drygin.popcornplan.features.details.presentation.DetailsScreenViewModel
import com.drygin.popcornplan.features.favorite.presentation.FavoriteScreenViewModel
import com.drygin.popcornplan.features.home.presentation.HomeScreenViewModel
import com.drygin.popcornplan.features.reminder.presentation.RemindersScreenViewModel
import com.drygin.popcornplan.features.search.presentation.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
val viewModelModule = module {
    viewModel { (savedStateHandle: SavedStateHandle) ->
        DetailsScreenViewModel(get(), get(), savedStateHandle)
    }
    viewModel { FavoriteScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { RemindersScreenViewModel(get(), get()) }
    viewModel { SearchScreenViewModel(get(), get()) }
}
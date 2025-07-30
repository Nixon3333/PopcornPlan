package com.drygin.popcornplan.app.di

import androidx.lifecycle.SavedStateHandle
import com.drygin.popcornplan.features.details.DetailsScreenViewModel
import com.drygin.popcornplan.features.favorite.FavoriteScreenViewModel
import com.drygin.popcornplan.features.trending.TrendingScreenViewModel
import com.drygin.popcornplan.features.reminder.RemindersScreenViewModel
import com.drygin.popcornplan.features.search.SearchScreenViewModel
import com.drygin.popcornplan.features.sync.WebSocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
val viewModelsModule = module {
    viewModel { (savedStateHandle: SavedStateHandle) ->
        DetailsScreenViewModel(get(), get(), savedStateHandle)
    }
    viewModel { FavoriteScreenViewModel(get()) }
    viewModel { TrendingScreenViewModel(get(), get()) }
    viewModel { RemindersScreenViewModel(get(), get(), get()) }
    viewModel { SearchScreenViewModel(get(), get()) }

    viewModel {
        WebSocketViewModel(
            syncWebSocketClient = get(),
            syncEventHandler = get(),
            get(),
            get()
        )
    }
}
package com.drygin.popcornplan.common.di

import com.drygin.popcornplan.features.details.di.detailsModule
import com.drygin.popcornplan.features.favorite.di.favoriteModule
import com.drygin.popcornplan.features.home.di.movieModule
import com.drygin.popcornplan.features.reminder.di.reminderModule
import com.drygin.popcornplan.features.search.di.searchModule

/**
 * Created by Drygin Nikita on 22.07.2025.
 */

val appModules = listOf(
    networkModule,
    databaseModule,
    viewModelModule,
    detailsModule,
    movieModule,
    favoriteModule,
    reminderModule,
    searchModule
)

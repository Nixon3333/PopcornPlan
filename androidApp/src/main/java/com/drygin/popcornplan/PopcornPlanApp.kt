package com.drygin.popcornplan

import android.app.Application
import com.drygin.popcornplan.common.di.databaseModule
import com.drygin.popcornplan.common.di.networkModule
import com.drygin.popcornplan.features.details.di.detailsModule
import com.drygin.popcornplan.features.favorite.di.favoriteModule
import com.drygin.popcornplan.features.home.di.movieModule
import com.drygin.popcornplan.features.reminder.di.reminderModule
import com.drygin.popcornplan.features.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
class PopcornPlanApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PopcornPlanApp)
            modules(
                networkModule,
                databaseModule,
                detailsModule,
                favoriteModule,
                movieModule,
                reminderModule,
                searchModule
            )
        }
    }
}
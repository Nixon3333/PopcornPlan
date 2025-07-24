package com.drygin.popcornplan.app

import android.app.Application
import com.drygin.popcornplan.app.di.notificationModule
import com.drygin.popcornplan.app.di.platformModule
import com.drygin.popcornplan.app.di.viewModelsModule
import com.drygin.popcornplan.di.databaseModule
import com.drygin.popcornplan.di.featureModule
import com.drygin.popcornplan.di.networkModule
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
                platformModule,
                notificationModule,
                networkModule,
                databaseModule,
                featureModule,
                viewModelsModule
            )
        }
    }
}
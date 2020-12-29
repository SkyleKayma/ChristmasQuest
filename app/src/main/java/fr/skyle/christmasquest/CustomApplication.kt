package fr.skyle.christmasquest

import android.app.Application
import fr.skyle.christmasquest.di.Modules
import fr.skyle.christmasquest.utils.AchievementsUtils
import fr.skyle.christmasquest.utils.PlayerUtils
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        // Needed to init data
        var playerUtils = get<PlayerUtils>()
        var achievementsUtils = get<AchievementsUtils>()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    Modules.serviceModule,
                    Modules.utilsModule,
                    Modules.repositoryModule,
                    Modules.viewModelModule
                )
            )
        }
    }
}
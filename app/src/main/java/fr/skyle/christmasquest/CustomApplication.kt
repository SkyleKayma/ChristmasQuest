package fr.skyle.christmasquest

import android.app.Application
import fr.skyle.christmasquest.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    Modules.serviceModule,
                    Modules.utilsModule,
                    Modules.viewModelModule
                )
            )
        }
    }
}
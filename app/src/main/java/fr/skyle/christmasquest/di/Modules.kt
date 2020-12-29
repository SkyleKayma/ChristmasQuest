package fr.skyle.christmasquest.di

import coil.ImageLoader
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.skyle.christmasquest.repository.AchievementRepository
import fr.skyle.christmasquest.repository.PlayerRepository
import fr.skyle.christmasquest.ui.home.HomeViewModel
import fr.skyle.christmasquest.ui.login.LoginViewModel
import fr.skyle.christmasquest.ui.register.RegisterViewModel
import fr.skyle.christmasquest.utils.AchievementsUtils
import fr.skyle.christmasquest.utils.DateUtils
import fr.skyle.christmasquest.utils.PlayerUtils
import fr.skyle.christmasquest.utils.PreferencesUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Modules {

    val serviceModule = module {
        single {
            ImageLoader.Builder(get())
                .okHttpClient { get(named("COIL")) }
                .crossfade(true)
                .build()
        }

        single {
            Firebase.database.reference
        }
    }

    val utilsModule = module {
        single {
            DateUtils(androidContext())
        }

        single {
            PreferencesUtils(androidContext())
        }

        single {
            PlayerUtils(get())
        }

        single {
            AchievementsUtils(get())
        }
    }

    val repositoryModule = module {
        single {
            AchievementRepository(get(), get())
        }

        single {
            PlayerRepository(get(), get(), get())
        }
    }

    val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
        viewModel { RegisterViewModel(get()) }
        viewModel { HomeViewModel(get(), get()) }
    }
}
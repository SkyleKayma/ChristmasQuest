package fr.skyle.christmasquest.di

import coil.ImageLoader
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.skyle.christmasquest.util.DateUtils
import org.koin.android.ext.koin.androidContext
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
    }

    val viewModelModule = module {
//        viewModel { SplashViewModel() }
    }
}
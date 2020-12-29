package fr.skyle.christmasquest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.SplashFragmentBinding
import fr.skyle.christmasquest.event.eventAchievementsLoaded
import fr.skyle.christmasquest.event.eventPlayersLoaded
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.utils.PreferencesUtils
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import org.koin.android.ext.android.inject
import timber.log.Timber

class SplashFragment : AbstractBindingFragment<SplashFragmentBinding>() {

    private val prefUtils by inject<PreferencesUtils>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        SplashFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        disposables.add(
            Flowable.combineLatest(
                eventPlayersLoaded.toFlowable(BackpressureStrategy.LATEST),
                eventAchievementsLoaded.toFlowable(BackpressureStrategy.LATEST),
                { playersLoaded, achievementsLoaded ->
                    playersLoaded to achievementsLoaded
                }
            ).fromIOToMain().subscribe({
                if (it.first && it.second) {
                    goToNextScreen()
                }
            }, {
                Timber.e(it, "Error waiting for data to load")
            })
        )
    }

    private fun goToNextScreen() {
        when {
            !prefUtils.isOnBoardingShown() ->
                navigate(SplashFragmentDirections.actionNavigationSplashToNavigationOnBoarding())
            prefUtils.playerId() == null ->
                navigate(SplashFragmentDirections.actionNavigationSplashToNavigationLoginRegister())
            !prefUtils.areRulesShown() ->
                navigate(SplashFragmentDirections.actionNavigationSplashToNavigationRules())
            else -> navigate(SplashFragmentDirections.actionNavigationSplashToNavigationHome())
        }
    }
}
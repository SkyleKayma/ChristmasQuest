package fr.skyle.christmasquest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.SplashFragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.utils.PreferencesUtils
import org.koin.android.ext.android.inject

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

        // TODO wait for data to be recovered at least one time to go further

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
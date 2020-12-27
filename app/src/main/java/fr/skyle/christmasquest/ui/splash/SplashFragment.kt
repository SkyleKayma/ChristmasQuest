package fr.skyle.christmasquest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.SplashFragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.util.PreferencesUtils
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

        when {
            !prefUtils.isOnBoardingShown() ->
                navigate(R.id.navigation_on_boarding)
            prefUtils.playerInfo() == null ->
                navigate(R.id.navigation_login_register)
            !prefUtils.areRulesShown() ->
                navigate(R.id.navigation_rules)
            else -> navigate(R.id.navigation_home)
        }
    }
}
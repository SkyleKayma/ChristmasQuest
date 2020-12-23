package fr.skyle.christmasquest.ui.onBoarding

import android.view.LayoutInflater
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.OnBoardingFragmentStep1Binding

class OnBoardingFragmentStep1 : AbstractBindingFragment<OnBoardingFragmentStep1Binding>() {

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        OnBoardingFragmentStep1Binding.inflate(inflater)

}
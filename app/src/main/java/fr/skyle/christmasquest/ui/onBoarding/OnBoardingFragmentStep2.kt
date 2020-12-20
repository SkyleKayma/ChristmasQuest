package fr.skyle.christmasquest.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.OnBoardingFragmentStep2Binding

class OnBoardingFragmentStep2 : AbstractBindingFragment<OnBoardingFragmentStep2Binding>() {

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        OnBoardingFragmentStep2Binding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    // --- Specific job
    // ---------------------------------------------------

}
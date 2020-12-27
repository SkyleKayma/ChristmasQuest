package fr.skyle.christmasquest.ui.onBoarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.skyle.christmasquest.ui.onBoarding.OnBoardingFragmentStep1
import fr.skyle.christmasquest.ui.onBoarding.OnBoardingFragmentStep2
import fr.skyle.christmasquest.ui.onBoarding.OnBoardingFragmentStep3
import fr.skyle.christmasquest.ui.onBoarding.OnBoardingFragmentStep4

class OnBoardingPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> OnBoardingFragmentStep1()
            1 -> OnBoardingFragmentStep2()
            2 -> OnBoardingFragmentStep3()
            else -> OnBoardingFragmentStep4()
        }

    companion object {
        const val NUM_PAGES = 4
    }
}
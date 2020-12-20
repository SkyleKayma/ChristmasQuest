package fr.skyle.christmasquest.ui.onBoarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.skyle.christmasquest.ui.onBoarding.*

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
            3 -> OnBoardingFragmentStep4()
            4 -> OnBoardingFragmentStep5()
            else -> OnBoardingFragmentStep6()
        }

    companion object {
        const val NUM_PAGES = 6
    }
}
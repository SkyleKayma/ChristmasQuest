package fr.skyle.christmasquest.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.skyle.christmasquest.ui.home.*

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragmentStep1()
            1 -> HomeFragmentStep2()
            2 -> HomeFragmentStep3()
            3 -> HomeFragmentStep4()
            else -> HomeFragmentStep5()
        }

    companion object {
        const val NUM_PAGES = 5
    }
}
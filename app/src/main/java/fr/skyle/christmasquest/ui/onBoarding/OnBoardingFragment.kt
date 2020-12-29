package fr.skyle.christmasquest.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.OnBoardingFragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.ext.popBackStack
import fr.skyle.christmasquest.ui.onBoarding.adapter.OnBoardingPagerAdapter
import fr.skyle.christmasquest.utils.PreferencesUtils
import org.koin.android.ext.android.inject

class OnBoardingFragment : AbstractBindingFragment<OnBoardingFragmentBinding>() {

    private val prefUtils by inject<PreferencesUtils>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        OnBoardingFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
        setListeners()
    }

    fun onBackPressed() {
        if (binding.viewPagerHome.currentItem == 0) {
            popBackStack()
        } else {
            binding.viewPagerHome.currentItem = binding.viewPagerHome.currentItem - 1
        }
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setViewPager() {
        val pagerAdapter = OnBoardingPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = pagerAdapter
    }

    private fun setListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })

        binding.buttonHomeContinue.setOnClickListener {
            if (binding.viewPagerHome.currentItem < OnBoardingPagerAdapter.NUM_PAGES - 1) {
                binding.viewPagerHome.currentItem = binding.viewPagerHome.currentItem + 1
            } else {
                prefUtils.isOnBoardingShown(true)
                navigate(OnBoardingFragmentDirections.actionNavigationOnBoardingToNavigationLoginRegister())
            }
        }
    }
}
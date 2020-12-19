package fr.skyle.christmasquest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.HomeFragmentBinding
import fr.skyle.christmasquest.ext.popBackStack
import fr.skyle.christmasquest.ui.home.adapter.HomePagerAdapter

class HomeFragment : AbstractBindingFragment<HomeFragmentBinding>() {

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        HomeFragmentBinding.inflate(inflater)

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
        val pagerAdapter = HomePagerAdapter(childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = pagerAdapter
    }

    private fun setListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })

        binding.buttonHomeContinue.setOnClickListener {
            if (binding.viewPagerHome.currentItem < HomePagerAdapter.NUM_PAGES) {
                binding.viewPagerHome.currentItem = binding.viewPagerHome.currentItem + 1
            } else {
                // Start new fragment
            }
        }
    }
}
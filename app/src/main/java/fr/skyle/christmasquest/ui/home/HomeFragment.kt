package fr.skyle.christmasquest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.EASTER_EGG_GIFT
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : AbstractBindingFragment<HomeFragmentBinding>() {

    private val model by viewModel<HomeViewModel>()

    private var easterEggGiftText = ""

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        HomeFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        // Easter egg gift buttons
        binding.imageViewHomeGift1.setOnClickListener {
            addTextToEasterEggGift(1)
        }

        binding.imageViewHomeGift2.setOnClickListener {
            addTextToEasterEggGift(2)
        }

        binding.imageViewHomeGift3.setOnClickListener {
            addTextToEasterEggGift(3)
        }

        binding.imageViewHomeGift4.setOnClickListener {
            addTextToEasterEggGift(4)
        }

        binding.imageViewHomeGift5.setOnClickListener {
            addTextToEasterEggGift(5)
        }

        binding.imageViewHomeGift6.setOnClickListener {
            addTextToEasterEggGift(6)
        }
    }

    private fun addTextToEasterEggGift(value: Int) {
        easterEggGiftText += value
        checkIfEasterEggGiftCompleted()
    }

    private fun checkIfEasterEggGiftCompleted() {
        if (easterEggGiftText.contains(COMBINATION_TO_FIND)) {
            model.addAchievementToPlayer(EASTER_EGG_GIFT)
        }
    }

    companion object {
        private const val COMBINATION_TO_FIND = "46213"
    }
}
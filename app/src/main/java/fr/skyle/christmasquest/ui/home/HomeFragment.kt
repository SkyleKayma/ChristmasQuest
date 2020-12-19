package fr.skyle.christmasquest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : AbstractBindingFragment<HomeFragmentBinding>() {

    private val model: HomeViewModel by viewModel()

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

    // --- Specific job
    // ---------------------------------------------------
    private fun setListeners() {

    }

    enum class State {
        LOADING,
        LOADED
    }
}
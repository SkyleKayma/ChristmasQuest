package fr.skyle.christmasquest.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.RulesFragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.utils.PreferencesUtils
import org.koin.android.ext.android.inject

class RulesFragment : AbstractBindingFragment<RulesFragmentBinding>() {

    private val prefUtils by inject<PreferencesUtils>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        RulesFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        binding.buttonRulesStart.setOnClickListener {
            prefUtils.areRulesShown(true)
            navigate(RulesFragmentDirections.actionNavigationRulesToNavigationHome())
        }
    }
}
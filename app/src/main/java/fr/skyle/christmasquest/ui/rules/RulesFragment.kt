package fr.skyle.christmasquest.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.RulesFragmentBinding
import fr.skyle.christmasquest.ext.navigate

class RulesFragment : AbstractBindingFragment<RulesFragmentBinding>() {

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
            navigate(R.id.navigation_login_register)
        }
    }
}
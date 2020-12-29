package fr.skyle.christmasquest.ui.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.LoginRegisterFragmentBinding
import fr.skyle.christmasquest.ext.navigate


class LoginRegisterFragment : AbstractBindingFragment<LoginRegisterFragmentBinding>() {

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        LoginRegisterFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        binding.buttonLoginRegisterLogin.setOnClickListener {
            navigate(LoginRegisterFragmentDirections.actionNavigationLoginRegisterToNavigationLogin())
        }

        binding.buttonLoginRegisterRegister.setOnClickListener {
            navigate(LoginRegisterFragmentDirections.actionNavigationLoginRegisterToNavigationRegister())
        }
    }
}
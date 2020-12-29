package fr.skyle.christmasquest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.jakewharton.rxbinding4.widget.textChangeEvents
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.LoginFragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.utils.PreferencesUtils
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class LoginFragment : AbstractBindingFragment<LoginFragmentBinding>() {

    private val model by viewModel<LoginViewModel>()
    private val prefUtils by inject<PreferencesUtils>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        LoginFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        return view
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        disposables.add(
            Observable.combineLatest(
                binding.textInputEditTextLoginPseudo.textChangeEvents(),
                binding.textInputEditTextLoginPassword.textChangeEvents(),
                { _, _ -> }
            ).fromIOToMain().subscribe({
                updateButtonEnableState()
            }, {
                Timber.e("Error on listening changes of pseudo/password")
            })
        )

        binding.buttonLoginValidate.setOnClickListener {
            requireActivity().hideKeyboard()
            checkIfPlayerExist()
        }
    }

    private fun checkIfPlayerExist() {
        val pseudo = binding.textInputEditTextLoginPseudo.textTrimmed()
        val password = binding.textInputEditTextLoginPassword.textTrimmed()

        val playerId = model.checkIfPlayerExist(pseudo, password)

        playerId?.let {
            prefUtils.playerId(it)

            // TODO send event to main activity to show snackbar

            snackbar(getString(R.string.login_success), Snackbar.LENGTH_SHORT)
            navigate(LoginFragmentDirections.actionNavigationLoginToNavigationRules())
        } ?: snackbar(getString(R.string.login_no_account_found), Snackbar.LENGTH_SHORT)
    }

    private fun updateButtonEnableState() {
        val isPseudoValid = binding.textInputEditTextLoginPseudo.textTrimmed().isNotEmpty()
        val isPasswordValid = binding.textInputEditTextLoginPassword.textTrimmed().isNotEmpty()

        binding.buttonLoginValidate.isEnabled =
            isPseudoValid && isPasswordValid
    }
}
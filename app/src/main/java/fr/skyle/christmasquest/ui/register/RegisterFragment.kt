package fr.skyle.christmasquest.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChangeEvents
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.RegisterFragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.utils.PreferencesUtils
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class RegisterFragment : AbstractBindingFragment<RegisterFragmentBinding>() {

    private val model by viewModel<RegisterViewModel>()
    private val prefUtils by inject<PreferencesUtils>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        RegisterFragmentBinding.inflate(inflater)

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
                binding.textInputEditTextRegisterPseudo.textChangeEvents(),
                binding.textInputEditTextRegisterPassword.textChangeEvents(),
                { _, _ -> }
            ).fromIOToMain().subscribe({
                updateButtonEnableState()
            }, {
                Timber.e("Error on listening changes of pseudo/password")
            })
        )

        binding.buttonRegisterValidate.setOnClickListener {
            requireActivity().hideKeyboard()
            checkIfPlayerAlreadyExist()
        }
    }

    private fun checkIfPlayerAlreadyExist() {
        val pseudo = binding.textInputEditTextRegisterPseudo.textTrimmed()
        val password = binding.textInputEditTextRegisterPassword.textTrimmed()

        val playerId = model.checkIfPlayerExist(pseudo, password)

        playerId?.let {
            snackbar(getString(R.string.register_pseudo_already_exist_error), Snackbar.LENGTH_SHORT)
        } ?: registerPlayer(pseudo, password)
    }

    private fun registerPlayer(pseudo: String, password: String) {
        val playerId = model.generatePlayerId()
        val task = model.registerPlayer(playerId, pseudo, password)

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                prefUtils.playerId(playerId)
                snackbar(getString(R.string.register_success), Snackbar.LENGTH_SHORT)
                navigate(RegisterFragmentDirections.actionNavigationRegisterToNavigationRules())
            } else {
                snackbar(R.string.register_pseudo_already_exist_error, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun updateButtonEnableState() {
        val isPseudoValid = binding.textInputEditTextRegisterPseudo.textTrimmed().isNotEmpty()
        val isPasswordValid = binding.textInputEditTextRegisterPassword.textTrimmed().isNotEmpty()

        binding.buttonRegisterValidate.isEnabled =
            isPseudoValid && isPasswordValid
    }
}
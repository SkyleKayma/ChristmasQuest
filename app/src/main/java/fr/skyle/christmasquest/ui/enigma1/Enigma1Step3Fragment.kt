package fr.skyle.christmasquest.ui.enigma1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_1_STEP_3
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma1Step3FragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class Enigma1Step3Fragment : AbstractBindingFragment<Enigma1Step3FragmentBinding>() {

    private val model by viewModel<EnigmaStep1ViewModel>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma1Step3FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        binding.buttonEnigma1Step3Check.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkIfTextEnteredIsValid()) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_1_STEP_3)) {
                    model.addAchievementToPlayer(ENIGMA_1_STEP_3).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            } else {
                binding.textInputEditTextEnigma1Step3.setText("")
                snackbar(R.string.enigma1_step3_answer_invalid, Snackbar.LENGTH_SHORT)
            }
        }

        disposables.add(
            binding.textInputEditTextEnigma1Step3.textChanges()
                .fromIOToMain().subscribe({
                    updateButtonDisplay()
                }, { Timber.e(it) })
        )
    }

    private fun updateButtonDisplay() {
        binding.buttonEnigma1Step3Check.isEnabled =
            binding.textInputEditTextEnigma1Step3.textTrimmed().isNotEmpty()
    }

    private fun checkIfTextEnteredIsValid(): Boolean =
        binding.textInputEditTextEnigma1Step3.textTrimmed().contains(TEXT_TO_CONTAINS, true)

    private fun goToNextScreen() {
        navigate(Enigma1Step3FragmentDirections.actionNavigationEnigma1Step3ToNavigationHome())
    }

    companion object {
        private const val TEXT_TO_CONTAINS = "ven"
    }
}
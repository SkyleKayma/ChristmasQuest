package fr.skyle.christmasquest.ui.enigma3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_3_STEP_2
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma3Step2FragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class Enigma3Step2Fragment : AbstractBindingFragment<Enigma3Step2FragmentBinding>() {

    private val model by viewModel<Enigma3ViewModel>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma3Step2FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        disposables.add(
            binding.textInputEditTextEnigma3Step2.textChanges()
                .fromIOToMain().subscribe({
                    updateButtonDisplay()
                }, { Timber.e(it) })
        )

        binding.buttonEnigma3Step2Check.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkIfTextEnteredIsValid()) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_3_STEP_2)) {
                    model.addAchievementToPlayer(ENIGMA_3_STEP_2).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            } else {
                binding.textInputEditTextEnigma3Step2.setText("")
                snackbar(R.string.enigma3_step2_answer_invalid, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun updateButtonDisplay() {
        binding.buttonEnigma3Step2Check.isEnabled =
            binding.textInputEditTextEnigma3Step2.textTrimmed().isNotEmpty()
    }

    private fun checkIfTextEnteredIsValid(): Boolean =
        binding.textInputEditTextEnigma3Step2.textTrimmed().contains(TEXT, true) ||
                binding.textInputEditTextEnigma3Step2.textTrimmed().contains(TEXT2, true)

    private fun goToNextScreen() {
        navigate(Enigma3Step2FragmentDirections.actionNavigationEnigma3Step2ToNavigationEnigma3Step3())
    }

    companion object {
        private const val TEXT = "Café"
        private const val TEXT2 = "Cafe"
    }
}
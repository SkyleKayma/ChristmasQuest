package fr.skyle.christmasquest.ui.enigma2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_2_STEP_3
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma2Step3FragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class Enigma2Step3Fragment : AbstractBindingFragment<Enigma2Step3FragmentBinding>() {

    private val model by viewModel<EnigmaStep2ViewModel>()

    private val events = listOf(
        R.string.enigma2_step3_text_choice_1,
        R.string.enigma2_step3_text_choice_2,
        R.string.enigma2_step3_text_choice_3,
        R.string.enigma2_step3_text_choice_4,
        R.string.enigma2_step3_text_choice_5,
        R.string.enigma2_step3_text_choice_6,
        R.string.enigma2_step3_text_choice_7,
        R.string.enigma2_step3_text_choice_8,
        R.string.enigma2_step3_text_choice_9,
        R.string.enigma2_step3_text_choice_10
    )

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma2Step3FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun initAdapter() {
        val adapterEvents = AdapterEnigma2Step2Events(events.map { getString(it) })

        binding.recyclerViewEnigma2Step3Events.apply {
            adapter = adapterEvents
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners() {
        disposables.add(
            binding.textInputEditTextEnigma2Step3.textChanges()
                .fromIOToMain().subscribe({
                    updateButtonDisplay()
                }, { Timber.e(it) })
        )

        binding.buttonEnigma2Step3Check.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkIfTextEnteredIsValid()) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_2_STEP_3)) {
                    model.addAchievementToPlayer(ENIGMA_2_STEP_3).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            } else {
                binding.textInputEditTextEnigma2Step3.setText("")
                snackbar(R.string.enigma2_step3_answer_invalid, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun updateButtonDisplay() {
        binding.buttonEnigma2Step3Check.isEnabled =
            binding.textInputEditTextEnigma2Step3.textTrimmed().isNotEmpty()
    }

    private fun checkIfTextEnteredIsValid(): Boolean =
        binding.textInputEditTextEnigma2Step3.textTrimmed().contains(TEXT, true) ||
                binding.textInputEditTextEnigma2Step3.textTrimmed().contains(TEXT2, true)

    private fun goToNextScreen() {
        navigate(Enigma2Step3FragmentDirections.actionNavigationEnigma2Step3ToNavigationHome())
    }

    companion object {
        private const val TEXT = "Décembre"
        private const val TEXT2 = "Decembre"
    }
}
package fr.skyle.christmasquest.ui.enigma2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_2_STEP_1
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma2Step1FragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.ui.enigma1.EnigmaStep1ViewModel
import fr.skyle.christmasquest.view.EnigmaWordToFillLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class Enigma2Step1Fragment : AbstractBindingFragment<Enigma2Step1FragmentBinding>() {

    private val model by viewModel<EnigmaStep2ViewModel>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma2Step1FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        val onButtonClickedListener = View.OnClickListener {
            it.isEnabled = false
            setLetter((it as Button).textTrimmed())
        }

        binding.buttonEnigma2Step1LetterA.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterABis.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterB.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterC.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterE.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterI.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterN.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterO.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterQ.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterR.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterS.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterT.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterU.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterX.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterXBis.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma2Step1LetterY.setOnClickListener(onButtonClickedListener)

        // Enigma item listener
        binding.enigmaWordToFillEnigma2Step1.listener = object : EnigmaWordToFillLayout.OnFilledListener {
            override fun onFilled() {
                checkIfWordIsValid()
            }
        }
    }

    private fun setLetter(letter: String) {
        binding.enigmaWordToFillEnigma2Step1.setLetter(letter)
    }

    private fun reset() {
        // Reset custom views
        binding.enigmaWordToFillEnigma2Step1.reset()

        // Reset all buttons
        binding.buttonEnigma2Step1LetterA.isEnabled = true
        binding.buttonEnigma2Step1LetterABis.isEnabled = true
        binding.buttonEnigma2Step1LetterB.isEnabled = true
        binding.buttonEnigma2Step1LetterC.isEnabled = true
        binding.buttonEnigma2Step1LetterE.isEnabled = true
        binding.buttonEnigma2Step1LetterI.isEnabled = true
        binding.buttonEnigma2Step1LetterN.isEnabled = true
        binding.buttonEnigma2Step1LetterO.isEnabled = true
        binding.buttonEnigma2Step1LetterQ.isEnabled = true
        binding.buttonEnigma2Step1LetterR.isEnabled = true
        binding.buttonEnigma2Step1LetterS.isEnabled = true
        binding.buttonEnigma2Step1LetterT.isEnabled = true
        binding.buttonEnigma2Step1LetterU.isEnabled = true
        binding.buttonEnigma2Step1LetterX.isEnabled = true
        binding.buttonEnigma2Step1LetterXBis.isEnabled = true
        binding.buttonEnigma2Step1LetterY.isEnabled = true
    }

    private fun checkIfWordIsValid() {
        if (binding.enigmaWordToFillEnigma2Step1.actualWord.contains(WORD_TO_FIND, true)) {
            if (!model.checkIfPlayerHaveAchievement(ENIGMA_2_STEP_1)) {
                model.addAchievementToPlayer(ENIGMA_2_STEP_1).addOnCompleteListener {
                    goToNextScreen()
                }
            } else goToNextScreen()
        } else {
            reset()
            snackbar(R.string.enigma2_step1_answer_invalid, Snackbar.LENGTH_SHORT)
        }
    }

    private fun goToNextScreen() {
        navigate(Enigma2Step1FragmentDirections.actionNavigationEnigma2Step1ToNavigationEnigma2Step2())
    }

    companion object {
        const val WORD_TO_FIND = "TRAINEAUX"
    }
}
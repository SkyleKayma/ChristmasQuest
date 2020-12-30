package fr.skyle.christmasquest.ui.enigma3

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_3_STEP_1
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma3Step1FragmentBinding
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.view.Enigma3WordToFillLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class Enigma3Step1Fragment : AbstractBindingFragment<Enigma3Step1FragmentBinding>() {

    private val model by viewModel<Enigma3ViewModel>()

    private var mediaPlayer: MediaPlayer? = null

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma3Step1FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        val onButtonClickedListener = View.OnClickListener {
            setNumber((it as Button).textTrimmed())
        }

        binding.buttonEnigma3Step1Number0.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number1.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number2.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number3.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number4.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number5.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number6.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number7.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number8.setOnClickListener(onButtonClickedListener)
        binding.buttonEnigma3Step1Number9.setOnClickListener(onButtonClickedListener)

        // Enigma item listener
        binding.enigmaWordToFillEnigma3Step1.listener = object : Enigma3WordToFillLayout.OnFilledListener {
            override fun onFilled() {
                checkIfWordIsValid()
            }
        }

        // Start song
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)

        mediaPlayer = MediaPlayer.create(context, R.raw.vive_le_vent_remix)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    private fun checkIfWordIsValid() {
        if (binding.enigmaWordToFillEnigma3Step1.actualWord.contains(TEXT, true)) {
            if (!model.checkIfPlayerHaveAchievement(ENIGMA_3_STEP_1)) {
                model.addAchievementToPlayer(ENIGMA_3_STEP_1).addOnCompleteListener {
                    goToNextScreen()
                }
            } else goToNextScreen()
        } else {
            reset()
            snackbar(R.string.enigma3_step1_answer_invalid, Snackbar.LENGTH_SHORT)
        }
    }

    private fun setNumber(number: String) {
        binding.enigmaWordToFillEnigma3Step1.setNumber(number)
    }

    private fun reset() {
        // Reset custom views
        binding.enigmaWordToFillEnigma3Step1.reset()
    }

    private fun goToNextScreen() {
        navigate(Enigma3Step1FragmentDirections.actionNavigationEnigma3Step1ToNavigationEnigma3Step2())
    }

    companion object {
        const val TEXT = "3165"
    }
}
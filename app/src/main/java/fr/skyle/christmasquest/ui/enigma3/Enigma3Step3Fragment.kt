package fr.skyle.christmasquest.ui.enigma3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import fr.openium.kotlintools.ext.*
import fr.skyle.christmasquest.ENIGMA_3_STEP_3
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma3Step3FragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.abs

class Enigma3Step3Fragment : AbstractBindingFragment<Enigma3Step3FragmentBinding>() {

    private val model by viewModel<Enigma3ViewModel>()

    private lateinit var sensorManager: SensorManager

    private var accelerometerListener: SensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        override fun onSensorChanged(sensorEvent: SensorEvent) {
            if (abs(sensorEvent.values[1]) > abs(sensorEvent.values[0])) {
                binding.textViewEnigma3Step3Location.gone()
            } else {
                if (sensorEvent.values[0] > 8 || sensorEvent.values[0] < -8) {
                    binding.textViewEnigma3Step3Location.show()
                } else {
                    binding.textViewEnigma3Step3Location.gone()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sensorManager.unregisterListener(accelerometerListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(accelerometerListener)
    }

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma3Step3FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init sensorManager
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        registerAccelerometerListener()

        disposables.add(
            binding.textInputEditTextEnigma3Step3.textChanges()
                .fromIOToMain().subscribe({
                    updateButtonDisplay()
                }, { Timber.e(it) })
        )

        binding.buttonEnigma3Step3Check.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkIfTextEnteredIsValid()) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_3_STEP_3)) {
                    model.addAchievementToPlayer(ENIGMA_3_STEP_3).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            } else {
                binding.textInputEditTextEnigma3Step3.setText("")
                snackbar(R.string.enigma3_step2_answer_invalid, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun registerAccelerometerListener() {
        sensorManager.registerListener(
            accelerometerListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun updateButtonDisplay() {
        binding.buttonEnigma3Step3Check.isEnabled =
            binding.textInputEditTextEnigma3Step3.textTrimmed().isNotEmpty()
    }

    private fun checkIfTextEnteredIsValid(): Boolean =
        binding.textInputEditTextEnigma3Step3.textTrimmed().contains(TEXT, true) ||
                (binding.textInputEditTextEnigma3Step3.textTrimmed().contains(TEXT2, true) &&
                        binding.textInputEditTextEnigma3Step3.textTrimmed().contains(TEXT3, true))

    private fun goToNextScreen() {
        navigate(Enigma3Step3FragmentDirections.actionNavigationEnigma3Step3ToNavigationHome())
    }

    companion object {
        private const val TEXT = "Mairie de Clermont"
        private const val TEXT2 = "10 Rue Philippe Marcombes"
        private const val TEXT3 = "63000"
    }
}
package fr.skyle.christmasquest.ui.enigma1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.skyle.christmasquest.ENIGMA_1_STEP_1
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma1Step1FragmentBinding
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.sqrt


class Enigma1Step1Fragment : AbstractBindingFragment<Enigma1Step1FragmentBinding>() {

    private val model by viewModel<EnigmaStep1ViewModel>()

    private lateinit var sensorManager: SensorManager
    private var cpt = 0

    private var accelerometerListener: SensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        override fun onSensorChanged(sensorEvent: SensorEvent) {
            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            val mAccelCurrent = sqrt(x * x + y * y + z * z)

            if (mAccelCurrent > 30.0) {
                println("New accelerometer value mAccelCurrent $mAccelCurrent")
                cpt++

                synchronized(cpt) {
                    checkIfTestIsValid()
                }
            }
        }
    }

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma1Step1FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init sensorManager
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(accelerometerListener)
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        registerAccelerometerListener()
    }

    private fun registerAccelerometerListener() {
        sensorManager.registerListener(
            accelerometerListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun checkIfTestIsValid() {
        if (cpt > 15) {
            if (!model.checkIfPlayerHaveAchievement(ENIGMA_1_STEP_1)) {
                model.addAchievementToPlayer(ENIGMA_1_STEP_1).addOnCompleteListener {
                    view?.post {
                        goToNextScreen()
                    }
                }
            } else goToNextScreen()
        }
    }

    private fun goToNextScreen() {
        navigate(Enigma1Step1FragmentDirections.actionNavigationEnigma1Step1ToNavigationEnigma1Step2())
    }
}
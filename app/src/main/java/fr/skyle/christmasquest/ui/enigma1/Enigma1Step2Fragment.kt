package fr.skyle.christmasquest.ui.enigma1

import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import fr.openium.kotlintools.ext.show
import fr.skyle.christmasquest.ENIGMA_1_STEP_2
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma1Step2FragmentBinding
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel


class Enigma1Step2Fragment : AbstractBindingFragment<Enigma1Step2FragmentBinding>() {

    private val model by viewModel<Enigma1ViewModel>()

    private lateinit var cameraManager: CameraManager
    private val torchCallback = object : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            super.onTorchModeChanged(cameraId, enabled)
            if (enabled) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_1_STEP_2)) {
                    model.addAchievementToPlayer(ENIGMA_1_STEP_2).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            }
        }
    }

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma1Step2FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraManager = requireContext().getSystemService(CameraManager::class.java)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            cameraManager.registerTorchCallback(torchCallback, null)
        } else {
            binding.linearLayoutEnigma1Step2NoFlash.show()
        }
    }

    private fun goToNextScreen() {
        navigate(Enigma1Step2FragmentDirections.actionNavigationEnigma1Step2ToNavigationEnigma1Step3())
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.unregisterTorchCallback(torchCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraManager.unregisterTorchCallback(torchCallback)
    }
}
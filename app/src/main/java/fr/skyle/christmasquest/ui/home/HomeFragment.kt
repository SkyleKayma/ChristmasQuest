package fr.skyle.christmasquest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import fr.openium.kotlintools.ext.snackbar
import fr.skyle.christmasquest.*
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.HomeFragmentBinding
import fr.skyle.christmasquest.event.eventHomeLoaded
import fr.skyle.christmasquest.event.eventPlayerAchievementsChanged
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.view.EnigmaItemLayout
import fr.skyle.christmasquest.view.SecretGiftItemLayout
import fr.skyle.christmasquest.view.SecretStarItemLayout
import fr.skyle.christmasquest.view.SecretTinselLightBulbItemLayout
import io.reactivex.rxjava3.core.BackpressureStrategy
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : AbstractBindingFragment<HomeFragmentBinding>() {

    private val model by viewModel<HomeViewModel>()

    private var secretGiftText = ""

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        HomeFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventHomeLoaded.onNext(Unit)

        setListeners()

        // Update displays
        updateDisplay()
    }

    // --- Other job
    // ---------------------------------------------------

    private fun setListeners() {
        disposables.add(
            eventPlayerAchievementsChanged.toFlowable(BackpressureStrategy.LATEST)
                .fromIOToMain()
                .subscribe({
                    updateDisplay()
                }, {
                    Timber.e(it, "Error listening to player achievements changes")
                })
        )

        // Secret gift buttons
        val onGiftResolvedListener = object : SecretGiftItemLayout.OnResolvedListener {
            override fun onResolved(value: Int) {
                if (!checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_GIFT)) {
                    addTextToSecretGift(value)
                }
            }
        }

        binding.secretGiftItem1.listener = onGiftResolvedListener
        binding.secretGiftItem2.listener = onGiftResolvedListener
        binding.secretGiftItem3.listener = onGiftResolvedListener
        binding.secretGiftItem4.listener = onGiftResolvedListener
        binding.secretGiftItem5.listener = onGiftResolvedListener
        binding.secretGiftItem6.listener = onGiftResolvedListener

        // Secret tinsel buttons
        val onTinselResolvedListener = object : SecretTinselLightBulbItemLayout.OnResolvedListener {
            override fun onResolved() {
                if (!checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_TINSEL)) {
                    checkIfSecretTinselCompleted()
                }
            }
        }

        binding.secretTinselLightBulbItem1.listener = onTinselResolvedListener
        binding.secretTinselLightBulbItem2.listener = onTinselResolvedListener
        binding.secretTinselLightBulbItem3.listener = onTinselResolvedListener
        binding.secretTinselLightBulbItem4.listener = onTinselResolvedListener
        binding.secretTinselLightBulbItem5.listener = onTinselResolvedListener
        binding.secretTinselLightBulbItem6.listener = onTinselResolvedListener

        // Secret star buttons
        val onStarResolvedListener = object : SecretStarItemLayout.OnResolvedListener {
            override fun onResolved() {
                if (!checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_STAR)) {
                    checkIfSecretStarsCompleted()
                }
            }
        }

        binding.secretStarItem1.listener = onStarResolvedListener
        binding.secretStarItem2.listener = onStarResolvedListener
        binding.secretStarItem3.listener = onStarResolvedListener

        // Enigma listeners
        binding.homeEnigmaLayout1.setOnClickListener {
            if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_1)) {
                navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma1Step1())
            } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_2)) {
                navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma1Step2())
            } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_3)) {
                navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma1Step3())
            }
        }

        binding.homeEnigmaLayout2.setOnClickListener {
            if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_3)) {
                if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_2_STEP_1)) {
                    navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma2Step1())
                } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_2_STEP_2)) {
                    navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma2Step2())
                } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_2_STEP_3)) {
                    navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma2Step3())
                }
            } else snackbar(R.string.home_enigma_prerequisite_enigma_1, Snackbar.LENGTH_SHORT)
        }

        binding.homeEnigmaLayout3.setOnClickListener {
            if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_3)) {
                if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_2_STEP_3)) {
                    if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_3_STEP_1)) {
                        navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma3Step1())
                    } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_3_STEP_2)) {
                        navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma3Step2())
                    } else if (!checkIfPlayerAlreadyHaveAchievement(ENIGMA_3_STEP_3)) {
                        navigate(HomeFragmentDirections.actionNavigationHomeToNavigationEnigma3Step3())
                    }
                } else snackbar(R.string.home_enigma_prerequisite_enigma_2, Snackbar.LENGTH_SHORT)
            } else snackbar(R.string.home_enigma_prerequisite_enigma_1, Snackbar.LENGTH_SHORT)
        }

        binding.homeEnigmaLayout1.listener = object : EnigmaItemLayout.OnStarClickedListener {
            override fun onStarClicked() {
                if (binding.homeEnigmaLayout1.isResolved) {
                    binding.secretStarItem1.setResolved()
                }
            }
        }

        binding.homeEnigmaLayout2.listener = object : EnigmaItemLayout.OnStarClickedListener {
            override fun onStarClicked() {
                if (binding.homeEnigmaLayout2.isResolved) {
                    binding.secretStarItem2.setResolved()
                }
            }
        }

        binding.homeEnigmaLayout3.listener = object : EnigmaItemLayout.OnStarClickedListener {
            override fun onStarClicked() {
                if (binding.homeEnigmaLayout3.isResolved) {
                    binding.secretStarItem3.setResolved()
                }
            }
        }
    }

    private fun updateDisplay() {
        initEnigmaStatus()
        initSecretGifts()
        initSecretTinselTab()
        initSecretStars()
    }

    private fun checkIfPlayerAlreadyHaveAchievement(achievementId: String): Boolean =
        model.checkIfPlayerHaveAchievement(achievementId)

    // Enigma
    private fun initEnigmaStatus() {
        if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_1_STEP_3)) {
            binding.homeEnigmaLayout1.setResolved()
        } else {
            binding.homeEnigmaLayout1.reset()
        }

        if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_2_STEP_3)) {
            binding.homeEnigmaLayout2.setResolved()
        } else {
            binding.homeEnigmaLayout2.reset()
        }

        if (checkIfPlayerAlreadyHaveAchievement(ENIGMA_3_STEP_3)) {
            binding.homeEnigmaLayout3.setResolved()
        } else {
            binding.homeEnigmaLayout3.reset()
        }
    }

    // Gift
    private fun initSecretGifts() {
        if (checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_GIFT)) {
            binding.secretGiftItem1.setResolved()
            binding.secretGiftItem2.setResolved()
            binding.secretGiftItem3.setResolved()
            binding.secretGiftItem4.setResolved()
            binding.secretGiftItem5.setResolved()
            binding.secretGiftItem6.setResolved()
        } else {
            binding.secretGiftItem1.reset()
            binding.secretGiftItem2.reset()
            binding.secretGiftItem3.reset()
            binding.secretGiftItem4.reset()
            binding.secretGiftItem5.reset()
            binding.secretGiftItem6.reset()
        }
    }

    private fun addTextToSecretGift(value: Int) {
        if (!secretGiftText.contains(value.toString(), true)) {
            secretGiftText += value
        }
        checkIfSecretGiftCompleted()
    }

    private fun checkIfSecretGiftCompleted() {
        val isValid = secretGiftText.contains(GIFT_COMBINATION_TO_FIND, true)

        if (secretGiftText.count() >= 6) {
            secretGiftText = ""

            if (isValid) {
                model.addAchievementToPlayer(SECRET_CHRISTMAS_GIFT)
            } else {
                binding.secretGiftItem1.reset()
                binding.secretGiftItem2.reset()
                binding.secretGiftItem3.reset()
                binding.secretGiftItem4.reset()
                binding.secretGiftItem5.reset()
                binding.secretGiftItem6.reset()
            }
        }
    }

    // Tinsel
    private fun initSecretTinselTab() {
        if (checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_TINSEL)) {
            binding.secretTinselLightBulbItem1.setResolved()
            binding.secretTinselLightBulbItem2.setResolved()
            binding.secretTinselLightBulbItem3.setResolved()
            binding.secretTinselLightBulbItem4.setResolved()
            binding.secretTinselLightBulbItem5.setResolved()
            binding.secretTinselLightBulbItem6.setResolved()
        } else {
            binding.secretTinselLightBulbItem1.reset()
            binding.secretTinselLightBulbItem2.reset()
            binding.secretTinselLightBulbItem3.reset()
            binding.secretTinselLightBulbItem4.reset()
            binding.secretTinselLightBulbItem5.reset()
            binding.secretTinselLightBulbItem6.reset()
        }
    }

    private fun checkIfSecretTinselCompleted() {
        if (binding.secretTinselLightBulbItem1.isResolved &&
            binding.secretTinselLightBulbItem2.isResolved &&
            binding.secretTinselLightBulbItem3.isResolved &&
            binding.secretTinselLightBulbItem4.isResolved &&
            binding.secretTinselLightBulbItem5.isResolved &&
            binding.secretTinselLightBulbItem6.isResolved
        ) {
            model.addAchievementToPlayer(SECRET_CHRISTMAS_TINSEL)
        }
    }

    // Star
    private fun initSecretStars() {
        if (checkIfPlayerAlreadyHaveAchievement(SECRET_CHRISTMAS_STAR)) {
            binding.secretStarItem1.setResolved()
            binding.secretStarItem2.setResolved()
            binding.secretStarItem3.setResolved()
        } else {
            binding.secretStarItem1.reset()
            binding.secretStarItem2.reset()
            binding.secretStarItem3.reset()
        }
    }

    private fun checkIfSecretStarsCompleted() {
        if (binding.secretStarItem1.isResolved &&
            binding.secretStarItem2.isResolved &&
            binding.secretStarItem3.isResolved
        ) {
            model.addAchievementToPlayer(SECRET_CHRISTMAS_STAR)
        }
    }

    companion object {
        private const val GIFT_COMBINATION_TO_FIND = "462135"
    }
}
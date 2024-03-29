package fr.skyle.christmasquest.ui.enigma3

import com.google.android.gms.tasks.Task
import fr.skyle.christmasquest.base.viewmodel.AbstractViewModel
import fr.skyle.christmasquest.repository.AchievementRepository
import fr.skyle.christmasquest.repository.PlayerRepository

class Enigma3ViewModel(
    private val achievementRepository: AchievementRepository,
    private val playerRepository: PlayerRepository
) : AbstractViewModel() {

    fun addAchievementToPlayer(achievementId: String): Task<MutableList<Task<*>>> =
        achievementRepository.addAchievement(achievementId)

    fun checkIfPlayerHaveAchievement(achievementId: String): Boolean =
        achievementRepository.checkIfPlayerAlreadyHaveAchievement(achievementId, playerRepository.getAchievementsForPlayer())
}
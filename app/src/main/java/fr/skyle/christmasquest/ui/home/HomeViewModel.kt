package fr.skyle.christmasquest.ui.home

import fr.skyle.christmasquest.base.viewmodel.AbstractViewModel
import fr.skyle.christmasquest.repository.AchievementRepository
import fr.skyle.christmasquest.repository.PlayerRepository

class HomeViewModel(
    private val achievementRepository: AchievementRepository,
    private val playerRepository: PlayerRepository
) : AbstractViewModel() {

    fun addAchievementToPlayer(achievementId: String) {
        if (!achievementRepository.checkIfPlayerAlreadyHaveAchievement(achievementId, playerRepository.getAchievementsForPlayer())) {
            achievementRepository.addAchievement(achievementId)
        }
    }
}
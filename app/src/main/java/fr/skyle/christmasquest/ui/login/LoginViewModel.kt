package fr.skyle.christmasquest.ui.login

import fr.skyle.christmasquest.base.viewmodel.AbstractViewModel
import fr.skyle.christmasquest.repository.AchievementRepository
import fr.skyle.christmasquest.repository.PlayerRepository

class LoginViewModel(
    private val playerRepository: PlayerRepository
) : AbstractViewModel() {

    fun checkIfPlayerExist(pseudo: String, password: String): String? =
        playerRepository.checkIfPlayerExist(pseudo, password)
}
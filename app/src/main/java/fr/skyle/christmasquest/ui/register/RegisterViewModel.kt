package fr.skyle.christmasquest.ui.register

import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import fr.openium.kotlintools.ext.snackbar
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.viewmodel.AbstractViewModel
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.model.Player
import fr.skyle.christmasquest.repository.AchievementRepository
import fr.skyle.christmasquest.repository.PlayerRepository

class RegisterViewModel(
    private val playerRepository: PlayerRepository
) : AbstractViewModel() {

    fun checkIfPlayerExist(pseudo: String, password: String): String? =
        playerRepository.checkIfPlayerExist(pseudo, password)

    fun generatePlayerId(): String =
        playerRepository.generatePlayerId()

    fun registerPlayer(playerId: String, pseudo: String, password: String): Task<Void> =
        playerRepository.registerPlayer(playerId, pseudo, password)
}
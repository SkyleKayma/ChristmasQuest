package fr.skyle.christmasquest.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.model.Player
import fr.skyle.christmasquest.utils.PlayerUtils
import fr.skyle.christmasquest.utils.PreferencesUtils

class PlayerRepository(
    val dbRef: DatabaseReference,
    val prefUtils: PreferencesUtils,
    val playerUtils: PlayerUtils
) {

    fun getAchievementsForPlayer(): List<String> =
        playerUtils.players.firstOrNull { it.id == prefUtils.playerId() ?: "" }?.achievements?.toList()?.map { it.first } ?: listOf()

    fun checkIfPlayerExist(pseudo: String, password: String): String? =
        playerUtils.players.firstOrNull { it.name == pseudo && it.password == password }?.id

    fun generatePlayerId(): String =
        dbRef.child(PLAYERS).push().key!!

    fun registerPlayer(playerId: String, pseudo: String, password: String): Task<Void> =
        dbRef.child(PLAYERS).child(playerId).setValue(Player(null, pseudo, password, hashMapOf()))
}
package fr.skyle.christmasquest.repository

import com.google.firebase.database.DatabaseReference
import fr.skyle.christmasquest.ACHIEVEMENTS
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.utils.PreferencesUtils
import java.util.*

class AchievementRepository(
    private val prefUtils: PreferencesUtils,
    private val dbRef: DatabaseReference
) {

    fun checkIfPlayerAlreadyHaveAchievement(achievementId: String, playerAchievements: List<String>): Boolean =
        playerAchievements.any { it == achievementId }

    fun addAchievement(achievementId: String) {
        prefUtils.playerId()?.let { playerId ->
            // First add to achievement list
            dbRef.child(ACHIEVEMENTS).child(achievementId).child(PLAYERS).updateChildren(
                hashMapOf<String, Any>().apply {
                    put(playerId, true)
                }
            )

            // Then add achievement to player
            dbRef.child(PLAYERS).child(playerId).child(ACHIEVEMENTS).updateChildren(
                hashMapOf<String, Any>().apply {
                    put(achievementId, Date().time)
                }
            )
        }
    }
}
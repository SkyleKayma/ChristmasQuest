package fr.skyle.christmasquest.utils

import android.content.Context
import androidx.preference.PreferenceManager


class PreferencesUtils(context: Context) {

    private val preferenceManager =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun isOnBoardingShown(isShown: Boolean) {
        preferenceManager.edit().apply {
            putBoolean(KEY_ON_BOARDING_SHOWN, isShown)
            apply()
        }
    }

    fun isOnBoardingShown(): Boolean =
        preferenceManager.getBoolean(KEY_ON_BOARDING_SHOWN, false)

    fun areRulesShown(areShown: Boolean) {
        preferenceManager.edit().apply {
            putBoolean(KEY_RULES_SHOWN, areShown)
            apply()
        }
    }

    fun areRulesShown(): Boolean =
        preferenceManager.getBoolean(KEY_RULES_SHOWN, false)

    fun playerId(id: String) {
        preferenceManager.edit().apply {
            putString(KEY_PLAYER_ID, id)
            apply()
        }
    }

    fun playerId(): String? =
        preferenceManager.getString(KEY_PLAYER_ID, null)

    companion object {
        private const val KEY_ON_BOARDING_SHOWN = "KEY_ON_BOARDING_SHOWN"
        private const val KEY_PLAYER_ID = "KEY_PLAYER_ID"
        private const val KEY_RULES_SHOWN = "KEY_RULES_SHOWN"
    }
}
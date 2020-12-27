package fr.skyle.christmasquest.util

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

    data class PlayerInfo(val id: String, val pseudo: String) {

        override fun toString(): String =
            listOf(id, pseudo).joinToString(SEPARATOR)

        companion object {
            private const val SEPARATOR = "%%%%%%"

            fun fromString(info: String): PlayerInfo {
                val values = info.split(SEPARATOR)
                return PlayerInfo(values[0], values[1])
            }
        }
    }

    fun areRulesShown(areShown: Boolean) {
        preferenceManager.edit().apply {
            putBoolean(KEY_RULES_SHOWN, areShown)
            apply()
        }
    }

    fun areRulesShown(): Boolean =
        preferenceManager.getBoolean(KEY_RULES_SHOWN, false)

    fun playerInfo(info: PlayerInfo) {
        preferenceManager.edit().apply {
            putString(KEY_PLAYER_INFO, info.toString())
            apply()
        }
    }

    fun playerInfo(): PlayerInfo? =
        preferenceManager.getString(KEY_PLAYER_INFO, null)?.let {
            PlayerInfo.fromString(it)
        }

    companion object {
        private const val KEY_ON_BOARDING_SHOWN = "KEY_ON_BOARDING_SHOWN"
        private const val KEY_PLAYER_INFO = "KEY_PLAYER_INFO"
        private const val KEY_RULES_SHOWN = "KEY_RULES_SHOWN"
    }
}
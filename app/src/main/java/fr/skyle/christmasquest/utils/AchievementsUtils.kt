package fr.skyle.christmasquest.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import fr.skyle.christmasquest.ACHIEVEMENTS
import fr.skyle.christmasquest.event.eventAchievementsLoaded
import fr.skyle.christmasquest.model.Achievement
import timber.log.Timber


class AchievementsUtils(dbRef: DatabaseReference) {

    var achievements: List<Achievement> = listOf()

    init {
        // Always up to date
        dbRef.child(ACHIEVEMENTS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val achievementsMap = dataSnapshot.getValue<HashMap<String, Achievement>>() ?: hashMapOf()

                // Transform to list
                achievements = achievementsMap.onEach {
                    it.value.id = it.key
                }.map { it.value }

                eventAchievementsLoaded.onNext(true)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error getting achievements in db $error")
            }
        })
    }
}
package fr.skyle.christmasquest.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.event.eventPlayersLoaded
import fr.skyle.christmasquest.model.Player
import timber.log.Timber


class PlayerUtils(dbRef: DatabaseReference) {

    var players: List<Player> = listOf()

    init {
        // Always up to date
        dbRef.child(PLAYERS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playersMap = dataSnapshot.getValue<HashMap<String, Player>>() ?: hashMapOf()

                // Transform to list
                players = playersMap.onEach {
                    it.value.id = it.key
                }.map { it.value }

                eventPlayersLoaded.onNext(true)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error getting achievements in db $error")
            }
        })
    }
}
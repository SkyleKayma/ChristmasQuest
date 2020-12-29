package fr.skyle.christmasquest.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import fr.openium.kotlintools.ext.snackbar
import fr.skyle.christmasquest.ACHIEVEMENTS
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.MainActivityBinding
import fr.skyle.christmasquest.utils.PreferencesUtils
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val dbRef by inject<DatabaseReference>()
    private val prefUtils by inject<PreferencesUtils>()

    // --- Lifecycle
    // ---------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init binding
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init toolbar
        binding.toolbarMain.toolbar.setupWithNavController(findNavController(R.id.nav_host_fragment_main_activity))

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        findNavController(R.id.nav_host_fragment_main_activity).addOnDestinationChangedListener { _, destination, _ ->
            // Needed to be done here cause OF FUCKING ANDROID implementation
            binding.toolbarMain.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)

            if (destination.id in listOf(
                    R.id.navigation_on_boarding,
                    R.id.navigation_login_register,
                    R.id.navigation_login,
                    R.id.navigation_register,
                    R.id.navigation_rules,
                    R.id.navigation_home
                )
            ) {
                binding.toolbarMain.toolbar.visibility = View.GONE
            } else {
                binding.toolbarMain.toolbar.visibility = View.VISIBLE
            }
        }
    }

    private fun onLoggedEvent() {
        // Player always up to date
        dbRef.child(PLAYERS).child(prefUtils.playerId() ?: "").child(ACHIEVEMENTS).addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snackbar(getString(R.string.achievement_easter_egg_gift), Snackbar.LENGTH_SHORT)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error getting achievements of player from db $error")
            }
        })
    }
}
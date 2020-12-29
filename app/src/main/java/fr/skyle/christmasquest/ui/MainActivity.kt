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
import fr.skyle.christmasquest.*
import fr.skyle.christmasquest.databinding.MainActivityBinding
import fr.skyle.christmasquest.event.eventHomeLoaded
import fr.skyle.christmasquest.event.eventPlayerAchievementsChanged
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.utils.PreferencesUtils
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val dbRef by inject<DatabaseReference>()
    private val prefUtils by inject<PreferencesUtils>()

    private val disposables = CompositeDisposable()

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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        findNavController(R.id.nav_host_fragment_main_activity).addOnDestinationChangedListener { _, destination, _ ->
            // Needed to be done here cause OF FUCKING ANDROID implementation
            binding.toolbarMain.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)

            if (destination.id in listOf(
                    R.id.navigation_splash,
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

        disposables.add(
            eventHomeLoaded.toFlowable(BackpressureStrategy.LATEST)
                .take(1)
                .fromIOToMain()
                .subscribe({
                    onLoggedEvent()
                }, {
                    Timber.e(it, "Error listening for logged event")
                })
        )
    }

    private fun onLoggedEvent() {
        // Players achievements always up to date
        dbRef.child(PLAYERS).child(prefUtils.playerId() ?: "").child(ACHIEVEMENTS).orderByValue().startAt(Date().time.toDouble())
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    when (snapshot.key) {
                        ENIGMA_FIRST ->
                            snackbar(getString(R.string.achievement_enigma_first), Snackbar.LENGTH_SHORT)
                        ENIGMA_SECOND ->
                            snackbar(getString(R.string.achievement_enigma_second), Snackbar.LENGTH_SHORT)
                        ENIGMA_THIRD ->
                            snackbar(getString(R.string.achievement_enigma_third), Snackbar.LENGTH_SHORT)
                        SECRET_CHRISTMAS_GIFT ->
                            snackbar(getString(R.string.achievement_secret_gift), Snackbar.LENGTH_SHORT)
                        SECRET_CHRISTMAS_TINSEL ->
                            snackbar(getString(R.string.achievement_secret_tinsel), Snackbar.LENGTH_SHORT)
                        SECRET_CHRISTMAS_STAR ->
                            snackbar(getString(R.string.achievement_secret_star), Snackbar.LENGTH_SHORT)
                    }

                    eventPlayerAchievementsChanged.onNext(Unit)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    eventPlayerAchievementsChanged.onNext(Unit)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    eventPlayerAchievementsChanged.onNext(Unit)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {
                    Timber.e("Error getting achievements of player from db $error")
                }
            })
    }
}
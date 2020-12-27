package fr.skyle.christmasquest.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

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
                    R.id.navigation_rules,
                    R.id.navigation_login_register,
                    R.id.navigation_login,
                    R.id.navigation_register
                )
            ) {
                binding.toolbarMain.toolbar.visibility = View.GONE
            } else {
                binding.toolbarMain.toolbar.visibility = View.VISIBLE
            }
        }
    }
}
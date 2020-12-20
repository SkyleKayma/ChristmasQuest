package fr.skyle.christmasquest.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    // --- Lifecycle
    // ---------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {

        findNavController(R.id.nav_host_fragment_main_activity).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in listOf(R.id.navigation_home, R.id.navigation_rules)) {
                binding.toolbarMain.toolbar.visibility = View.GONE
            } else {
                binding.toolbarMain.toolbar.visibility = View.VISIBLE
            }
        }
    }
}
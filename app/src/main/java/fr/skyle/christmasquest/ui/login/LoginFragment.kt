package fr.skyle.christmasquest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.jakewharton.rxbinding4.widget.textChangeEvents
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.PLAYERS
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.LoginFragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.model.Player
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject
import timber.log.Timber


class LoginFragment : AbstractBindingFragment<LoginFragmentBinding>() {

    private val dbRef by inject<DatabaseReference>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        LoginFragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        return view
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        disposables.add(
            Observable.combineLatest(
                binding.textInputEditTextLoginPseudo.textChangeEvents(),
                binding.textInputEditTextLoginPassword.textChangeEvents(),
                { _, _ -> }
            ).fromIOToMain().subscribe({
                updateButtonEnableState()
            }, {
                Timber.e("Error on listening changes of pseudo/password")
            })
        )

        binding.buttonLoginValidate.setOnClickListener {
            requireActivity().hideKeyboard()
            checkIfPlayerExist()
        }
    }

    private fun checkIfPlayerExist() {
        val pseudo = binding.textInputEditTextLoginPseudo.textTrimmed()
        val password = binding.textInputEditTextLoginPassword.textTrimmed()

        dbRef.child(PLAYERS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.getValue<HashMap<String,Player>>() ?: hashMapOf()
                val player = result.values.filter { it.name == pseudo && it.password == password }.firstOrNull()

                player?.let {
                    snackbar(getString(R.string.login_login_success), Snackbar.LENGTH_SHORT)
                    // TODO navigate to enter a game screen
                } ?: snackbar(getString(R.string.login_login_fail), Snackbar.LENGTH_SHORT)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Timber.e("Error getting player in db $error")
                snackbar(getString(R.string.generic_error), Snackbar.LENGTH_SHORT)
            }
        })
    }

    private fun updateButtonEnableState() {
        val isPseudoValid = binding.textInputEditTextLoginPseudo.textTrimmed().isNotEmpty()
        val isPasswordValid = binding.textInputEditTextLoginPassword.textTrimmed().isNotEmpty()

        binding.buttonLoginValidate.isEnabled =
            isPseudoValid && isPasswordValid
    }
}
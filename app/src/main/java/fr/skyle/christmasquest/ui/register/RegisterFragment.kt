package fr.skyle.christmasquest.ui.register

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
import fr.skyle.christmasquest.databinding.RegisterFragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import fr.skyle.christmasquest.model.Player
import fr.skyle.christmasquest.util.PreferencesUtils
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject
import timber.log.Timber


class RegisterFragment : AbstractBindingFragment<RegisterFragmentBinding>() {

    private val prefUtils by inject<PreferencesUtils>()
    private val dbRef by inject<DatabaseReference>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        RegisterFragmentBinding.inflate(inflater)

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
                binding.textInputEditTextRegisterPseudo.textChangeEvents(),
                binding.textInputEditTextRegisterPassword.textChangeEvents(),
                { _, _ -> }
            ).fromIOToMain().subscribe({
                updateButtonEnableState()
            }, {
                Timber.e("Error on listening changes of pseudo/password")
            })
        )

        binding.buttonRegisterValidate.setOnClickListener {
            requireActivity().hideKeyboard()
            checkIfPlayerAlreadyExist()
        }
    }

    private fun checkIfPlayerAlreadyExist() {
        val pseudo = binding.textInputEditTextRegisterPseudo.textTrimmed()
        val password = binding.textInputEditTextRegisterPassword.textTrimmed()

        dbRef.child(PLAYERS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.getValue<HashMap<String, Player>>() ?: hashMapOf()
                val player = result.values.filter { it.name == pseudo }.firstOrNull()

                player?.let {
                    snackbar(getString(R.string.register_pseudo_already_exist_error), Snackbar.LENGTH_SHORT)
                } ?: registerPlayer(pseudo, password)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Timber.e("Error getting player in db $error")
                snackbar(getString(R.string.generic_error), Snackbar.LENGTH_SHORT)
            }
        })
    }

    private fun registerPlayer(pseudo: String, password: String) {
        val id = dbRef.child(PLAYERS).push()
        val task = id.setValue(Player(pseudo, password))

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                prefUtils.playerInfo(PreferencesUtils.PlayerInfo(id.key ?: "", pseudo))
                snackbar(getString(R.string.register_register_success), Snackbar.LENGTH_SHORT)
                navigate(R.id.navigation_rules)
            } else {
                snackbar(R.string.register_pseudo_already_exist_error, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun updateButtonEnableState() {
        val isPseudoValid = binding.textInputEditTextRegisterPseudo.textTrimmed().isNotEmpty()
        val isPasswordValid = binding.textInputEditTextRegisterPassword.textTrimmed().isNotEmpty()

        binding.buttonRegisterValidate.isEnabled =
            isPseudoValid && isPasswordValid
    }
}
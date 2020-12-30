package fr.skyle.christmasquest.ui.enigma2

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import fr.openium.kotlintools.ext.getColorCompat
import fr.openium.kotlintools.ext.hideKeyboard
import fr.openium.kotlintools.ext.snackbar
import fr.openium.kotlintools.ext.textTrimmed
import fr.skyle.christmasquest.ENIGMA_2_STEP_2
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.base.fragment.AbstractBindingFragment
import fr.skyle.christmasquest.databinding.Enigma2Step2FragmentBinding
import fr.skyle.christmasquest.ext.fromIOToMain
import fr.skyle.christmasquest.ext.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class Enigma2Step2Fragment : AbstractBindingFragment<Enigma2Step2FragmentBinding>() {

    private val model by viewModel<EnigmaStep2ViewModel>()

    // --- Binding
    // ---------------------------------------------------

    override fun inflate(inflater: LayoutInflater) =
        Enigma2Step2FragmentBinding.inflate(inflater)

    // --- Life cycle
    // ---------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    // --- Specific job
    // ---------------------------------------------------

    private fun setListeners() {
        initText()

        disposables.add(
            binding.textInputEditTextEnigma2Step2.textChanges()
                .fromIOToMain().subscribe({
                    updateButtonDisplay()
                }, { Timber.e(it) })
        )

        binding.buttonEnigma2Step2Check.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkIfTextEnteredIsValid()) {
                if (!model.checkIfPlayerHaveAchievement(ENIGMA_2_STEP_2)) {
                    model.addAchievementToPlayer(ENIGMA_2_STEP_2).addOnCompleteListener {
                        goToNextScreen()
                    }
                } else goToNextScreen()
            } else {
                binding.textInputEditTextEnigma2Step2.setText("")
                snackbar(R.string.enigma2_step2_answer_invalid, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun initText() {
        val initialText = requireContext().getString(R.string.enigma2_step2_text_2)

        val part1 = requireContext().getString(R.string.enigma2_step2_text_2_part_1)
        val part2 = requireContext().getString(R.string.enigma2_step2_text_2_part_2)
        val part3 = requireContext().getString(R.string.enigma2_step2_text_2_part_3)

        val ss = SpannableString(initialText)

        // Set part 1 click
        val part1Span = object : ClickableSpan() {
            override fun onClick(textView: View) {
                setTextInColor(binding.textViewEnigma2Step2Text, ss.indexOf(part1), ss.indexOf(part1) + part1.length)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(
            part1Span,
            ss.indexOf(part1),
            ss.indexOf(part1) + part1.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        // Set part 2 click
        val part2Span = object : ClickableSpan() {
            override fun onClick(textView: View) {
                setTextInColor(binding.textViewEnigma2Step2Text, ss.indexOf(part2), ss.indexOf(part2) + part2.length)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(
            part2Span,
            ss.indexOf(part2),
            ss.indexOf(part2) + part2.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        // Set part 3 click
        val part3Span = object : ClickableSpan() {
            override fun onClick(textView: View) {
                setTextInColor(binding.textViewEnigma2Step2Text, ss.indexOf(part3), ss.indexOf(part3) + part3.length)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(
            part3Span,
            ss.indexOf(part3),
            ss.indexOf(part3) + part3.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        binding.textViewEnigma2Step2Text.text = ss
        binding.textViewEnigma2Step2Text.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setTextInColor(view: TextView, start: Int, end: Int) {
        val spanText = view.text as SpannableString

        val fcs = ForegroundColorSpan(requireContext().getColorCompat(R.color.colorYellow))
        spanText.setSpan(fcs, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        binding.textViewEnigma2Step2Text.text = spanText
    }

    private fun updateButtonDisplay() {
        binding.buttonEnigma2Step2Check.isEnabled =
            binding.textInputEditTextEnigma2Step2.textTrimmed().isNotEmpty()
    }

    private fun checkIfTextEnteredIsValid(): Boolean =
        binding.textInputEditTextEnigma2Step2.textTrimmed().contains(TEXT, true)

    private fun goToNextScreen() {
        navigate(Enigma2Step2FragmentDirections.actionNavigationEnigma2Step2ToNavigationEnigma2Step3())
    }

    companion object {
        private const val TEXT = "oui"
    }
}
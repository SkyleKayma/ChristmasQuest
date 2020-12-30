package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import fr.skyle.christmasquest.databinding.EnigmaWordToFillLayoutBinding
import fr.skyle.christmasquest.ui.enigma2.Enigma2Step1Fragment

class EnigmaWordToFillLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: EnigmaWordToFillLayoutBinding =
        EnigmaWordToFillLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnFilledListener? = null
    var actualWord = ""

    private val views = listOf(
        binding.enigmaWordToFillItem1,
        binding.enigmaWordToFillItem2,
        binding.enigmaWordToFillItem3,
        binding.enigmaWordToFillItem4,
        binding.enigmaWordToFillItem5,
        binding.enigmaWordToFillItem6,
        binding.enigmaWordToFillItem7,
        binding.enigmaWordToFillItem8,
        binding.enigmaWordToFillItem9
    )

    fun setLetter(letter: String) {
        actualWord += letter
        updateDisplay()
    }

    fun reset() {
        actualWord = ""
        updateDisplay()
    }

    private fun updateDisplay() {
        views.forEach {
            it.textViewEnigmaWordToFillItemLetter.text = ""
        }

        actualWord.toCharArray().forEachIndexed { index, letter ->
            views[index].textViewEnigmaWordToFillItemLetter.text = letter.toString()

            if (index == Enigma2Step1Fragment.WORD_TO_FIND.length - 1) {
                listener?.onFilled()
            }
        }
    }

    interface OnFilledListener {

        /**
         * Called each time all letters are filled
         */
        fun onFilled()
    }
}
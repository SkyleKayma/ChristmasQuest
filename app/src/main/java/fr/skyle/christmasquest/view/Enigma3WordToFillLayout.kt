package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import fr.skyle.christmasquest.databinding.Enigma3WordToFillLayoutBinding
import fr.skyle.christmasquest.ui.enigma3.Enigma3Step1Fragment

class Enigma3WordToFillLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: Enigma3WordToFillLayoutBinding =
        Enigma3WordToFillLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnFilledListener? = null
    var actualWord = ""

    private val views = listOf(
        binding.enigma3WordToFillItem1,
        binding.enigma3WordToFillItem2,
        binding.enigma3WordToFillItem3,
        binding.enigma3WordToFillItem4
    )

    fun setNumber(number: String) {
        actualWord += number
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

        actualWord.toCharArray().forEachIndexed { index, number ->
            views[index].textViewEnigmaWordToFillItemLetter.text = number.toString()

            if (index == Enigma3Step1Fragment.TEXT.length - 1) {
                listener?.onFilled()
            }
        }
    }

    interface OnFilledListener {

        /**
         * Called each time all numbers are filled
         */
        fun onFilled()
    }
}
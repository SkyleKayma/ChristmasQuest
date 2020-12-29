package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import fr.openium.kotlintools.ext.dip
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.EnigmaItemLayoutBinding

class EnigmaItemLayout(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private var binding: EnigmaItemLayoutBinding =
        EnigmaItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        // Set style
        cardElevation = dip(2)
        radius = dip(8)

        computeAttribute(attrs)
    }

    private fun computeAttribute(attrs: AttributeSet?) {
        attrs?.let {
            val a =
                context.theme.obtainStyledAttributes(it, R.styleable.EnigmaItemLayout, 0, 0)

            try {
                val number = a.getString(R.styleable.EnigmaItemLayout_appNumber)
                binding.textViewEnigmaItemNumber.text = context.getString(R.string.enigma_number, number)

                val title = a.getString(R.styleable.EnigmaItemLayout_appTitle)
                binding.textViewEnigmaItemTitle.text = title

                val subtitle = a.getString(R.styleable.EnigmaItemLayout_appSubtitle)
                binding.textViewEnigmaItemSubtitle.text = subtitle
            } finally {
                a.recycle()
            }
        }
    }

    fun setResolved() {
        binding.imageViewEnigmaItemCompletion.setImageResource(R.drawable.ic_star_complete)
        binding.textViewEnigmaItemSubtitle.setCompoundDrawables(null, null, null, null)
    }
}
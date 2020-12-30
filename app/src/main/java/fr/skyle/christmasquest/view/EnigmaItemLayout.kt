package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import fr.openium.kotlintools.ext.dip
import fr.openium.kotlintools.ext.getDrawableCompat
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.EnigmaItemLayoutBinding

class EnigmaItemLayout(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private var binding: EnigmaItemLayoutBinding =
        EnigmaItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnStarClickedListener? = null
    var isResolved: Boolean = false

    init {
        // Set style
        cardElevation = dip(2)
        radius = dip(8)

        // Attr
        computeAttribute(attrs)

        // Listeners
        binding.imageViewEnigmaItemCompletion.setOnClickListener {
            listener?.onStarClicked()
        }
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
        isResolved = true
        binding.imageViewEnigmaItemCompletion.setImageResource(R.drawable.ic_star_complete)
        binding.textViewEnigmaItemSubtitle.setCompoundDrawables(null, null, null, null)
    }

    fun reset() {
        isResolved = false
        binding.imageViewEnigmaItemCompletion.setImageResource(R.drawable.ic_star_uncomplete)
        binding.textViewEnigmaItemSubtitle.setCompoundDrawables(null, null, context.getDrawableCompat(R.drawable.ic_arrow_right), null)
    }

    interface OnStarClickedListener {

        /**
         * Called each time the star is clicked
         */
        fun onStarClicked()
    }
}
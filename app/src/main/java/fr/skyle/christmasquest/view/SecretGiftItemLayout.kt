package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.SecretGiftItemLayoutBinding

class SecretGiftItemLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: SecretGiftItemLayoutBinding =
        SecretGiftItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnResolvedListener? = null
    var isResolved: Boolean = false

    private var imageIdResolved: Int? = null
    private var imageIdNotResolved: Int? = null
    private var value: Int = -1

    init {
        // Attr
        computeAttribute(attrs)

        // Listeners
        setOnClickListener {
            setResolved()
            listener?.onResolved(value)
        }
    }

    private fun computeAttribute(attrs: AttributeSet?) {
        attrs?.let {
            val a =
                context.theme.obtainStyledAttributes(it, R.styleable.SecretGiftItemLayout, 0, 0)

            try {
                imageIdResolved = a.getResourceId(R.styleable.SecretGiftItemLayout_imageIdResolved, R.drawable.ic_gift_1_open)
                imageIdNotResolved = a.getResourceId(R.styleable.SecretGiftItemLayout_imageIdNotResolved, R.drawable.ic_gift_1)
                value = a.getInt(R.styleable.SecretGiftItemLayout_value, -1)

                imageIdNotResolved?.let {
                    binding.imageViewSecretGiftItemCompletion.setImageResource(it)
                }
            } finally {
                a.recycle()
            }
        }
    }

    fun setResolved() {
        isResolved = true
        imageIdResolved?.let {
            binding.imageViewSecretGiftItemCompletion.setImageResource(it)
        }
    }

    fun reset() {
        isResolved = false
        imageIdNotResolved?.let {
            binding.imageViewSecretGiftItemCompletion.setImageResource(it)
        }
    }

    interface OnResolvedListener {

        /**
         * Called each time the light bulb is clicked
         */
        fun onResolved(value: Int)
    }
}
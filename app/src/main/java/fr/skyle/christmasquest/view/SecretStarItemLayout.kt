package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.SecretStarItemLayoutBinding

class SecretStarItemLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: SecretStarItemLayoutBinding =
        SecretStarItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnResolvedListener? = null
    var isResolved: Boolean = false

    fun setResolved() {
        isResolved = true
        binding.imageViewSecretStarItemCompletion.setImageResource(R.drawable.ic_star_secret_complete)

        listener?.onResolved()
    }

    fun reset() {
        isResolved = false
        binding.imageViewSecretStarItemCompletion.setImageResource(R.drawable.ic_star_secret_uncomplete)
    }

    interface OnResolvedListener {

        /**
         * Called each time the star is clicked
         */
        fun onResolved()
    }
}
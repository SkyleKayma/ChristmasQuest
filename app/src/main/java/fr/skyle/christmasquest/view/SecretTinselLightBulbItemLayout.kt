package fr.skyle.christmasquest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import fr.skyle.christmasquest.R
import fr.skyle.christmasquest.databinding.SecretTinselLightBulbItemLayoutBinding

class SecretTinselLightBulbItemLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: SecretTinselLightBulbItemLayoutBinding =
        SecretTinselLightBulbItemLayoutBinding.inflate(LayoutInflater.from(context), this)

    var listener: OnResolvedListener? = null
    var isResolved: Boolean = false

    init {
        setOnClickListener {
            setResolved()
            listener?.onResolved()
        }
    }

    fun setResolved() {
        isResolved = true
        binding.imageViewSecretTinselLightBulbItemCompletion.setImageResource(R.drawable.ic_tinsel_light_bulb_enabled)
    }

    fun reset() {
        isResolved = false
        binding.imageViewSecretTinselLightBulbItemCompletion.setImageResource(R.drawable.ic_tinsel_light_bulb_disabled)
    }

    interface OnResolvedListener {

        /**
         * Called each time the light bulb is clicked
         */
        fun onResolved()
    }
}
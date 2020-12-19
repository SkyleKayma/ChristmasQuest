package fr.skyle.christmasquest.ext

import android.app.Activity
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.navigation.findNavController
import fr.openium.kotlintools.ext.getColorCompat

fun Activity.setStatusBarColorRes(@ColorRes idColor: Int) {
    window?.statusBarColor = applicationContext.getColorCompat(idColor)
}

fun Activity.setStatusBarColor(color: Int) {
    window?.statusBarColor = color
}

fun Activity.navigate(navHostFragmentId: Int, resId: Int, bundle: Bundle? = null) {
    findNavController(navHostFragmentId).navigate(resId, bundle)
}
package fr.skyle.christmasquest.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(navDirections: NavDirections, navOptions: NavOptions? = null) {
    findNavController().navigate(navDirections, navOptions)
}

fun Fragment.navigate(@IdRes resId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
    findNavController().navigate(resId, args, navOptions)
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.popBackStack(@IdRes destinationId: Int, inclusive: Boolean) {
    findNavController().popBackStack(destinationId, inclusive)
}
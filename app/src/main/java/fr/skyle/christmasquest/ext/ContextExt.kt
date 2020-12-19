package fr.skyle.christmasquest.ext

import android.content.Context
import android.content.res.ColorStateList
import fr.openium.kotlintools.ext.getColorCompat

fun Context.getStatusBarHeightInPixels(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.getColorStateListFromColor(colorId: Int): ColorStateList =
    ColorStateList.valueOf(getColorCompat(colorId))
package me.ibrahimsn.lib.ext.SmoothBottomBar

import android.content.Context
import kotlin.math.roundToInt

internal fun Context.d2p(dp: Float): Float {
    return (dp * resources.displayMetrics.density).roundToInt().toFloat()
}

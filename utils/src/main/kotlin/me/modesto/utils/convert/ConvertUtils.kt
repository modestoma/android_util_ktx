@file:Suppress("NOTHING_TO_INLINE")

package me.modesto.utils.convert

import android.content.res.Resources
import android.util.TypedValue

/**
 * convert dp to px
 *
 * @author Created by Modesto in 2022/5/5
 */
val Number.toPx
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)

/**
 * convert px to dp
 *
 * @author Created by Modesto in 2022/5/5
 */
val Number.toDp
    get() = this.toFloat() / Resources.getSystem().displayMetrics.density

/**
 * converting px value to sp value
 *
 * @param value [Float] sp value
 * @return [Float] px value
 * @author Created by Modesto in 2022/4/19
 */
inline fun px2sp(value: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, Resources.getSystem().displayMetrics)

/**
 * converting px value to sp value
 *
 * @param value [Float] px value
 * @return [Float] sp value
 * @author Created by Modesto in 2022/4/19
 */
inline fun sp2px(value: Float): Float = value / Resources.getSystem().displayMetrics.scaledDensity + 0.5F
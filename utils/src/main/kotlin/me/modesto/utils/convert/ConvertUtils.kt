@file:Suppress("NOTHING_TO_INLINE")

package me.modesto.utils.convert

import android.content.res.Resources

/**
 * converting dp value to px value
 *
 * @param value [Float] dp value
 * @return [Float] px value
 * @author Created by Modesto in 2022/4/19
 */
inline fun dp2px(value: Float): Float = value * Resources.getSystem().displayMetrics.density + 0.5F

/**
 * converting px value to dp value
 *
 * @param value [Float] px value
 * @return [Float] dp value
 * @author Created by Modesto in 2022/4/19
 */
inline fun px2dp(value: Float): Float = value / Resources.getSystem().displayMetrics.density + 0.5F

/**
 * converting sp value to px value
 *
 * @param value [Float] sp value
 * @return [Float] px value
 * @author Created by Modesto in 2022/4/19
 */
inline fun sp2px(value: Float): Float = value * Resources.getSystem().displayMetrics.scaledDensity + 0.5F

/**
 * converting px value to sp value
 *
 * @param value [Float] px value
 * @return [Float] sp value
 * @author Created by Modesto in 2022/4/19
 */
inline fun px2sp(value: Float): Float = value / Resources.getSystem().displayMetrics.scaledDensity + 0.5F
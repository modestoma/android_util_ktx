package me.modesto.utils

import android.content.Context
import android.widget.Toast

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
inline fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, content, duration).show()
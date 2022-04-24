package me.modesto.utils.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/22
 */
fun Context.hasPermissions(vararg permissions: String): Boolean {
    if (permissions.isEmpty()) {
        return true
    }
    permissions.forEach { permission ->
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, permission)) {
            return false
        }
    }
    return true
}
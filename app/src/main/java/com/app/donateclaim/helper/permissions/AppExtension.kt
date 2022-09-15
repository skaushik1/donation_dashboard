package com.app.donateclaim.helper.permissions

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.app.donateclaim.R
import kotlin.collections.ArrayList

/**********************************************************************
 * This section is for application custom extension functions
 **********************************************************************/


const val TAG = "AppExtension"

/**
 * This method is used to encrypt the password and send on server.
 * @return
 */
fun String.encryptPassword(): String {
    return this
}

/**
 * This extension method is used to get application version
 */
//fun Context.getApplicationVersion(): String {
//    return getString(
//            R.string.lbl_application_version,
//            applicationContext.packageManager.getPackageInfo(packageName, 0).versionName
//    )
//}

/**
 * This extension function is used to get device token from pref
 */
//fun Context.getDeviceToken(): String {
//    return PrefData(this).getStringPrefs(PrefData.FcmToken).toString()
//}

/**
 * This extension function is used to bitmap from drawable
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }
    val width = if (bounds.isEmpty) intrinsicWidth else bounds.width()
    val height = if (bounds.isEmpty) intrinsicHeight else bounds.height()
    return Bitmap.createBitmap(width.nonZero(), height.nonZero(), Bitmap.Config.ARGB_8888)
            .also {
                val canvas = Canvas(it)
                setBounds(0, 0, canvas.width, canvas.height)
                draw(canvas)
            }
}

private fun Int.nonZero() = if (this <= 0) 1 else this

/**
 * This extension function is used to validate location value and pass 0
 */
fun Double?.toValidateLocationValue(): Double {
    var locationValue = this
    if (locationValue == null) {
        locationValue = 0.0
    }
    return locationValue
}

/**
 * This extension function is used to get width of device
 */
fun getWidth(context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels
}

/**
 * This extension function is used to get height of device
 */
fun getHeight(context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return displayMetrics.heightPixels
}

@RequiresApi(Build.VERSION_CODES.M)
fun NotificationManager.isNotificationAvailable(notificationId:Int): Boolean {
    val notifications =activeNotifications
    for (notification in notifications) {
        if (notification.id == notificationId) {
            return true
        }
    }
    return false
}

fun Context.isAppOnForeground(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses = activityManager.runningAppProcesses ?: return false
    val packageName = packageName
    for (appProcess in appProcesses) {
        if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
            return true
        }
    }
    return false
}
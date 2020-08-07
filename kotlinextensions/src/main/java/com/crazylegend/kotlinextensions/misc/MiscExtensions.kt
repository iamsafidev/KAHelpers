package com.crazylegend.kotlinextensions.misc

import android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import android.content.Context
import android.content.Intent
import android.content.pm.FeatureInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.crazylegend.kotlinextensions.context.isIgnoringBatteryOptimization
import java.util.*


/**
 * Created by crazy on 2/25/20 to long live and prosper !
 */

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)


fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


/**
 * https://android.googlesource.com/platform/cts/+/2b87267/tests/tests/graphics/src/android/opengl/cts/OpenGlEsVersionTest.java
 */
fun Context.getOpenGLVersion(): Int {
    val featureInfos = packageManager.systemAvailableFeatures
    if (featureInfos.isNotEmpty()) {
        for (featureInfo in featureInfos) {
            // Null feature name means this feature is the open gl es version feature.
            if (featureInfo.name == null) {
                return if (featureInfo.reqGlEsVersion != FeatureInfo.GL_ES_VERSION_UNDEFINED) {
                    getMajorVersion(featureInfo.reqGlEsVersion)
                } else {
                    1 // Lack of property means OpenGL ES version 1
                }
            }
        }
    }
    return 1
}

/** @see FeatureInfo.getGlEsVersion
 */
private fun getMajorVersion(glEsVersion: Int): Int {
    return glEsVersion and -0x10000 shr 16
}


inline fun <T, R> T.ifThis(predicate: T.() -> Boolean, trueFun: () -> R, elseFun: () -> R) = if (predicate()) {
    trueFun()
} else {
    elseFun()
}

@RequiresPermission(REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
fun Context.requestBatteryOptimizations() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val intent = Intent()
        val ignoring = isIgnoringBatteryOptimization ?: false
        if (!ignoring) {
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }
    }
}
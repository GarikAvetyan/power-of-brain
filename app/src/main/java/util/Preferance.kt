package util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


object Preferance {
    private val SOUND = "SOUND"

    fun setBooleanPreferance(activity: Activity, key: String, boolean: Boolean) {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences(SOUND, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, boolean)
        editor.apply()
    }

    fun getBooleanPreferance(activity: Activity, key: String): Boolean {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences(SOUND, Context.MODE_PRIVATE)

        return sharedPreferences.getBoolean(key, true)
    }
}
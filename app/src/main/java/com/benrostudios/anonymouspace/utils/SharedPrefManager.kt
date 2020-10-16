package com.benrostudios.anonymouspace.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


class SharedPrefManager(val context: Context) {
    private fun getPrefs() = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    var jwtStored: String
        get() = getPrefs()?.getString(PREFS_JWT, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_JWT, value)?.apply()
        }

    var anonName: String
        get() = getPrefs()?.getString(PREFS_USERNAME, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_USERNAME, value)?.apply()
        }

    fun nukeSharedPrefs() {
        getPrefs()?.edit()?.clear()?.apply()
    }

    companion object {
        const val PREFS_FILENAME = "com.benrostudios.anonymouspace"
        const val PREFS_USERNAME = "username"
        const val PREFS_JWT = "jwt"
    }
}
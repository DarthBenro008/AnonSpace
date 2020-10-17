package com.benrostudios.anonymouspace.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


class SharedPrefManager(val context: Context) {
    private fun getPrefs() = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    var uuid: String
        get() = getPrefs()?.getString(PREFS_JWT, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_JWT, value)?.apply()
        }

    var currentChatRoomId: String
        get() = getPrefs()?.getString(PREFS_CHATROOM, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_CHATROOM, value)?.apply()
        }

    var anonName: String
        get() = getPrefs()?.getString(PREFS_USERNAME, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_USERNAME, value)?.apply()
        }

    var screenTime: String
        get() = getPrefs()?.getString(PREFS_SCREENTIME, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_SCREENTIME, value)?.apply()
        }

    var interactions: String
        get() = getPrefs()?.getString(PREFS_INTERACTIONS, "") ?: ""
        set(value) {
            getPrefs()?.edit()?.putString(PREFS_INTERACTIONS, value)?.apply()
        }


    fun nukeSharedPrefs() {
        getPrefs()?.edit()?.clear()?.apply()
    }

    companion object {
        const val PREFS_FILENAME = "com.benrostudios.anonymouspace"
        const val PREFS_USERNAME = "username"
        const val PREFS_CHATROOM = "chatroom"
        const val PREFS_SCREENTIME = "screentime"
        const val PREFS_INTERACTIONS = "interactions"
        const val PREFS_JWT = "jwt"
    }
}
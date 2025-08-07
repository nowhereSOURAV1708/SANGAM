package com.example.sangam01

import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private const val PREF_NAME = "UserSession"
    private const val KEY_ADMIN_EMAIL = "admin_email"

    fun saveAdminEmail(context: Context, email: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ADMIN_EMAIL, email)
        editor.apply()
    }

    fun getAdminEmail(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_ADMIN_EMAIL, null)
    }

    fun clearSession(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

package com.edc.ad.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    const val PREFS_NAME = "EDC"

    // Login Status yes / no
    fun getLoginStatus(context: Context): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("loginstatus", "no")
        return text
    }

    fun saveLoginStatusFlag(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("loginstatus", text)
        editor.commit()
    }

    fun getUserName(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("username", "no")
        return text
    }

    fun saveUserName(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("username", text)
        editor.commit()
    }

    fun getAccessToken(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("accesstoken", "")
        return text
    }

    fun saveAccessToken(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("accesstoken", text)
        editor.commit()
    }

    fun getRefreshToken(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("refreshtoken", "")
        return text
    }

    fun saveRefreshToken(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("refreshtoken", text)
        editor.commit()
    }

    fun getStudentID(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("sid", "")
        return text
    }

    fun saveStudentID(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("sid", text)
        editor.commit()
    }

    fun getFCMToken(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("fcm_token", "")
        return text
    }

    fun setFCMToken(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("fcm_token", text)
        editor.commit()
    }
}
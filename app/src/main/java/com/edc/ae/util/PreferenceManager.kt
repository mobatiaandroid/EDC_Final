package com.edc.ae.util

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

    fun getNotificationStatus(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("notification_status", "")
        return text
    }

    fun saveNotificationStatus(context: Activity, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("notification_status", text)
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


    fun getEnrollStatus(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("enroll_status", "")
        return text
    }

    fun setEnrollStatus(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("enroll_status", text)
        editor.commit()
    }
    fun getStudentNo(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("student_no", "")
        return text
    }

    fun setStudentNo(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("student_no", text)
        editor.commit()
    }
    fun getTrafficNo(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("traffic_no", "")
        return text
    }

    fun setTrafficNo(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("traffic_no", text)
        editor.commit()
    }
    fun getTryFileNo(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("try_file_no", "")
        return text
    }

    fun setTryFileNo(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("try_file_no", text)
        editor.commit()
    }
    fun getTrainingLanguage(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("training_language", "")
        return text
    }

    fun setTrainingLanguage(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("training_language", text)
        editor.commit()
    }
    fun getMotherTongue(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("mother_tongue", "")
        return text
    }

    fun setMotherTongue(context: Context, text: Int) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("mother_tongue", text.toString())
        editor.commit()
    }
    fun getNationality(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("nationality", "")
        return text
    }

    fun setNationality(context: Context, text: Int) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("nationality", text.toString())
        editor.commit()
    }
    fun getEducation(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("education_level", "")
        return text
    }

    fun setEducation(context: Context, text: Int) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("education_level", text.toString())
        editor.commit()
    }
    fun getEmail(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("email", "")
        return text
    }

    fun setEmail(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("email", text.toString())
        editor.commit()
    }
    fun getPassword(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("password", "")
        return text
    }

    fun setPassword(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("password", text.toString())
        editor.commit()
    }
    fun getStudentStatus(context: Activity): String? {
        val settings: SharedPreferences
        val text: String?
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        text = settings.getString("status", "")
        return text
    }

    fun setStudentStatus(context: Context, text: String) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.putString("status", text.toString())
        editor.commit()
    }
}
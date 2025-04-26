package com.aos_kotlin.preference

import android.content.Context
import android.content.SharedPreferences

class WorkPreference(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var standardWorkHours: Int
        get() = preferences.getInt(KEY_STANDARD_WORK_HOURS, 8)
        set(value) = preferences.edit().putInt(KEY_STANDARD_WORK_HOURS, value).apply()

    var standardWorkMinutes: Int
        get() = preferences.getInt(KEY_STANDARD_WORK_MINUTES, 0)
        set(value) = preferences.edit().putInt(KEY_STANDARD_WORK_MINUTES, value).apply()

    var isOvertimeNotificationEnabled: Boolean
        get() = preferences.getBoolean(KEY_OVERTIME_NOTIFICATION, true)
        set(value) = preferences.edit().putBoolean(KEY_OVERTIME_NOTIFICATION, value).apply()

    companion object {
        private const val PREFS_NAME = "work_preferences"
        private const val KEY_STANDARD_WORK_HOURS = "standard_work_hours"
        private const val KEY_STANDARD_WORK_MINUTES = "standard_work_minutes"
        private const val KEY_OVERTIME_NOTIFICATION = "overtime_notification"
    }
} 
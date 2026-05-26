package com.example.floatingmouse

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("FloatingMousePrefs", Context.MODE_PRIVATE)

    fun setMouseEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("mouse_enabled", enabled).apply()
    }

    fun isMouseEnabled(): Boolean {
        return prefs.getBoolean("mouse_enabled", false)
    }

    fun setCursorSize(size: Int) {
        prefs.edit().putInt("cursor_size", size).apply()
    }

    fun getCursorSize(): Int {
        return prefs.getInt("cursor_size", 50)
    }

    fun setMovementSpeed(speed: Int) {
        prefs.edit().putInt("movement_speed", speed).apply()
    }

    fun getMovementSpeed(): Int {
        return prefs.getInt("movement_speed", 5)
    }
}
package com.example.praktikom.data.local.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface SessionLocalDataSource {
    fun saveLoginState(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    fun clearSession()
}

@Singleton
class SessionLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : SessionLocalDataSource {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "praktikom_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    override fun saveLoginState(isLoggedIn: Boolean) {
        prefs.edit { putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }
    }

    override fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    override fun clearSession() {
        prefs.edit { clear() }
    }
}
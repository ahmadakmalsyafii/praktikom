package com.example.praktikom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PraktikomApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}
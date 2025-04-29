package raf.console.chitalka

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp
import raf.console.chitalka.crash.CrashHandler

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
    }
}







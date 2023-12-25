package com.example.oneminutecountdown

import android.app.Application
import com.example.oneminutecountdown.di.appModule
import com.example.oneminutecountdown.notification.NotificationUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin{
            androidContext(this@MainApplication)
            modules(appModule)
        }

        NotificationUtils.createNotificationChannel(this)
    }
}
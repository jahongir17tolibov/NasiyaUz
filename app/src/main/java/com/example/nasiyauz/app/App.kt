package com.example.nasiyauz.app

import android.app.Application
import com.example.nasiyauz.data.sharedPref.AppPreferences
import com.example.nasiyauz.di.appModules
import com.example.nasiyauz.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        private lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPreferences.init(instance)

        startKoin {
            androidLogger()
            androidContext(instance)
            modules(appModules, viewModelModule)
        }

    }

}
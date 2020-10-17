package com.benrostudios.anonymouspace.ui

import android.app.Application
import com.benrostudios.anonymouspace.di.appComponent
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AnonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(this@AnonApplication))
            .build()
        startKoin {
            androidLogger()
            androidContext(this@AnonApplication)
            koin.loadModules(appComponent)
            koin.createRootScope()
        }
    }
}
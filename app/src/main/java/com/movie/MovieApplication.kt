package com.movie

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.movie.di.appModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class MovieApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin(this, appModule)
//        Fabric.with(this, Crashlytics())
    }
}
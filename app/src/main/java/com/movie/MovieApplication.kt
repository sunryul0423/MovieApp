package com.movie

import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.movie.di.appModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class MovieApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModule)
        Fabric.with(this, Crashlytics())
    }
}
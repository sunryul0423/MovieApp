package com.movie

import androidx.multidex.MultiDexApplication
import com.movie.di.appModule
import org.koin.android.ext.android.startKoin

class MovieApplication : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModule)
    }
}
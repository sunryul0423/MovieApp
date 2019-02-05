package com.movie

import android.support.multidex.MultiDexApplication

class MovieApplication : MultiDexApplication() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var INSTANCE: MovieApplication? = null
    }
}
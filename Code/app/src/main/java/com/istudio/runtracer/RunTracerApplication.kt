package com.istudio.runtracer

import android.app.Application
import android.util.Log
import timber.log.Timber


class RunTracerApplication : Application() {

    companion object {
        private val LOG_TAG = RunTracerApplication::class.simpleName

    }

    override fun onCreate() {
        super.onCreate()
        // Logging
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }



}
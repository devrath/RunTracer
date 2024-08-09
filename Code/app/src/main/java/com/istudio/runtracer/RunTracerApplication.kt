package com.istudio.runtracer

import android.app.Application
import android.util.Log
import com.istudio.auth.data.di.authDataModule
import com.istudio.auth.presentation.di.authViewModelModule
import com.istudio.core.data.networking.di.coreDataModule
import com.istudio.run.presentation.di.runViewModelModule
import com.istudio.runtracer.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber


class RunTracerApplication : Application() {

    companion object {
        private val LOG_TAG = RunTracerApplication::class.simpleName

    }

    val applicationScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()
        // Logging
        initTimber()
        // Di
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@RunTracerApplication)
            modules(
                authViewModelModule,
                authDataModule,
                coreDataModule,
                appModule,
                runViewModelModule
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }



}
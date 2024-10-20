package com.istudio.runtracer

import android.app.Application
import androidx.work.WorkerFactory
import com.istudio.auth.data.di.authDataModule
import com.istudio.auth.presentation.di.authViewModelModule
import com.istudio.core.data.networking.di.coreDataModule
import com.istudio.run.location.di.locationModule
import com.istudio.runtracer.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber

class RunTracerApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunTracerApplication)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                locationModule
            )
        }
    }
}
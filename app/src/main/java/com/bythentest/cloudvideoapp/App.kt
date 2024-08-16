package com.bythentest.cloudvideoapp

import android.app.Application
import com.bythentest.cloudvideoapp.di.networkModule
import com.bythentest.cloudvideoapp.di.repositoryModule
import com.bythentest.cloudvideoapp.di.useCaseModule
import com.cloudinary.Configuration
import com.cloudinary.android.MediaManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MediaManager.init(
            this,
            Configuration().apply {
                cloudName = CLOUD_NAME
                apiKey = BuildConfig.CLOUDINARY_API_KEY
                apiSecret = BuildConfig.CLOUDINARY_API_SECRET
            }
        )
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                )
            )
        }
    }

    companion object {
        private const val CLOUD_NAME = "dw9hnn0l3"
    }
}

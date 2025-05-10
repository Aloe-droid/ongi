package com.aloe_droid.ongi.ui

import android.app.Application
import com.aloe_droid.ongi.BuildConfig
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class OnGiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        NaverMapSdk.getInstance(this).client = NaverMapSdk
            .NcpKeyClient(BuildConfig.CLIENT_ID)
    }
}

package com.example.acharyaprashant

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ImageCache.initialize(this)
    }
}

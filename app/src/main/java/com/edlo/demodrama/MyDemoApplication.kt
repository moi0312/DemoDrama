package com.edlo.demodrama

import android.app.Application

class MyDemoApplication: Application() {
    companion object {
        lateinit var INSTANCE: MyDemoApplication
        private set
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}

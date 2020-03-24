package com.edlo.demovideolistwithroom

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.edlo.demogithub.util.Log

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

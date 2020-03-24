package com.edlo.demovideolistwithroom.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.edlo.demovideolistwithroom.BuildConfig
import com.edlo.demovideolistwithroom.MyDemoApplication

class SharedPreferencesHelper {
    private enum class Keys {
        STR_SEARCH_KEY,
    }

    companion object {
        val HELPER: SharedPreferencesHelper by lazy {
            SharedPreferencesHelper(MyDemoApplication.INSTANCE.applicationContext)
        }
        private lateinit var instance: SharedPreferences
    }

    private constructor(context: Context) {
        instance = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    fun reset(): SharedPreferencesHelper {
        instance.edit(commit = true) { clear() }
        return this
    }

    var searchKey: String
        get() {
           return try {
               instance.getString(Keys.STR_SEARCH_KEY.name, "")
           } catch (e: ClassCastException) {
               ""
           } as String
        }
        set(value) {
            instance.edit(commit = true) {
                putString(Keys.STR_SEARCH_KEY.name,  value)
            }
        }
}
package com.edlo.demodrama.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edlo.demodrama.net.NetworkCallback
import com.edlo.demodrama.net.NetworkCallbackListener

abstract class BaseViewModel: ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsLoading(): LiveData<Boolean> { return isLoading }

    private var networkAvailable: MutableLiveData<Boolean> = MutableLiveData()
    fun getNetworkAvailable(): LiveData<Boolean> { return networkAvailable }

    init {
        NetworkCallback.INSTANCE.addListener(object: NetworkCallbackListener {
            override fun onConnected() {
                networkAvailable.postValue(true)
            }
            override fun onDisconnected() {
                networkAvailable.postValue(false)
            }
        })
    }
}
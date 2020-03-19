package com.edlo.demovideolistwithroom.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsLoading(): LiveData<Boolean> { return isLoading }
}
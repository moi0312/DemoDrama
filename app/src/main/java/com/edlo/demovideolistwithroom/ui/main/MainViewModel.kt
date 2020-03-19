package com.edlo.demovideolistwithroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edlo.demogithub.util.Log
import com.edlo.demovideolistwithroom.db.Drama
import com.edlo.demovideolistwithroom.ui.base.BaseViewModel
import com.example.testcoroutines.net.ApiChocoHelper
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private var dramas: MutableLiveData<ArrayList<Drama>> = MutableLiveData(ArrayList())
    fun getDramas(): LiveData<ArrayList<Drama>> { return dramas }

    fun listDramasSample() {
        isLoading.value = true
        viewModelScope.launch {
            val listDrama = ApiChocoHelper.INSTANCE.listDramasSample()
            if(listDrama != null) {
                dramas.value = listDrama
            } else {
                Log.e("listDrama fail: " )
                dramas.value = ArrayList()
            }
            isLoading.value = false
        }
    }
}

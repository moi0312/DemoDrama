package com.edlo.demovideolistwithroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edlo.demogithub.util.Log
import com.edlo.demovideolistwithroom.db.Drama
import com.edlo.demovideolistwithroom.db.DramaDB
import com.edlo.demovideolistwithroom.net.NetworkCallback
import com.edlo.demovideolistwithroom.net.NetworkCallbackListener
import com.edlo.demovideolistwithroom.ui.base.BaseViewModel
import com.edlo.demovideolistwithroom.util.SharedPreferencesHelper
import com.example.testcoroutines.net.ApiChocoHelper
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

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

    private var dramas: MutableLiveData<ArrayList<Drama>> = MutableLiveData(ArrayList())
    fun getDramas(): LiveData<ArrayList<Drama>> { return dramas }

    private var searchKey: MutableLiveData<String> = MutableLiveData("")
    fun getSearchKey(): LiveData<String> { return searchKey }

    fun listDramasSample() {
        isLoading.value = true
        viewModelScope.launch {
            val listDrama = ApiChocoHelper.INSTANCE.listDramasSample()
            val dramaDao = DramaDB.getDatabase().dramaDao()
            listDrama?.let {
                dramaDao.insertAll(listDrama)
            } ?: run {
                Log.e("listDrama fail: " )
            }
            searchDrama()
            isLoading.value = false
        }
    }

    fun searchDrama(key: String = SharedPreferencesHelper.HELPER.searchKey) {
        SharedPreferencesHelper.HELPER.searchKey = key
        searchKey.value = key

        viewModelScope.launch {
            val dramaDao = DramaDB.getDatabase().dramaDao()
            searchKey.value?.let {
                if(it.isNotEmpty()) {
                    dramas.value = dramaDao.findByName("%${it}%") as ArrayList<Drama>
                } else {
                    dramas.value = dramaDao.getAll() as ArrayList<Drama>
                }
            }
        }
    }
}

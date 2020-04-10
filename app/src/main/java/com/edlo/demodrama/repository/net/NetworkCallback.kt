package com.edlo.demodrama.repository.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.edlo.demodrama.util.Log
import com.edlo.demodrama.MyDemoApplication

class NetworkCallback: ConnectivityManager.NetworkCallback() {
    companion object {
        val INSTANCE: NetworkCallback = NetworkCallback()
    }

    var networkAvailable: Boolean = false
    var listeners: HashSet<NetworkCallbackListener> = HashSet()

    init{
        val connectivityManager = MyDemoApplication.INSTANCE.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.requestNetwork(
            NetworkRequest.Builder().build(),
            object: ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.i("network onAvailable")
                    networkAvailable = true
                    for(listener in listeners) {
                        listener.onConnected()
                    }
                }
                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.i("network onLost")
                    networkAvailable = false
                    for(listener in listeners) {
                        listener.onDisconnected()
                    }
                }
//                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
//                    super.onCapabilitiesChanged(network, networkCapabilities)
//                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
////                            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
////                                Log.d("network onCapabilitiesChanged: wifi")
////                            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
////                                Log.d("network onCapabilitiesChanged: mobile")
////                            } else {
////                                Log.d("network onCapabilitiesChanged: other")
////                            }
//                    }
//                }
            }
        )
    }

    fun addListener(listener: NetworkCallbackListener) {
        listeners.add(listener)
    }
    fun removeListener(listener: NetworkCallbackListener) {
        listeners.remove(listener)
    }

}

interface NetworkCallbackListener {
    fun onConnected()
    fun onDisconnected()
}

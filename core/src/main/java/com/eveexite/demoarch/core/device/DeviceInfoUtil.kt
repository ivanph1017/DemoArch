package com.eveexite.demoarch.core.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class DeviceInfoUtil(context: Context) {

    private val connectivityManager: ConnectivityManager
            = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    var isOnline: Boolean = false
        private set // the setter is private and has the default implementation

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            isOnline = false
        }

        override fun onUnavailable() {
            isOnline = false
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            isOnline = false
        }

        override fun onAvailable(network: Network) {
            isOnline = true
        }
    }

    init {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            //silent exception
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

}
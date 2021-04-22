package com.shreyas.squaretakehomeapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Network Repository to get the current network status if Wifi/Cellular and handle
 *  Service fetch for list of employees.
 */
interface NetworkStatusRepository {
    val networkStatus: ConflatedBroadcastChannel<NetworkStatus>

    sealed class NetworkStatus {
        object NoNetworkAvailable : NetworkStatus()

        object WiFi : NetworkStatus()

        object Cellular : NetworkStatus()
    }
}

@Singleton
open class NetworkStatusRepositoryImpl @Inject constructor(context: Context) :
    NetworkStatusRepository {

    override val networkStatus = ConflatedBroadcastChannel<NetworkStatusRepository.NetworkStatus>()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        // Registering network callback on different connections, currently considering WiFi/Mobile only
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                connectivityManager.activeNetwork?.let { connectedNetwork ->
                    connectivityManager.getNetworkCapabilities(connectedNetwork)
                }?.also { connectedNetworkCapabilities ->
                    when {
                        connectedNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            setNetworkStatus(NetworkStatusRepository.NetworkStatus.WiFi)
                        }
                        connectedNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            setNetworkStatus(NetworkStatusRepository.NetworkStatus.Cellular)
                        }
                    }
                } ?: setNetworkStatus(NetworkStatusRepository.NetworkStatus.NoNetworkAvailable)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                setNetworkStatus(NetworkStatusRepository.NetworkStatus.NoNetworkAvailable)
            }
        })
    }

    private fun setNetworkStatus(status: NetworkStatusRepository.NetworkStatus) {
        MainScope().launch {
            networkStatus.send(status)
        }
    }
}
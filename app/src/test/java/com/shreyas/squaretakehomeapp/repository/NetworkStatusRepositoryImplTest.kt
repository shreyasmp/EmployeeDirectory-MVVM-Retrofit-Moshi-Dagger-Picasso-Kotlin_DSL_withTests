package com.shreyas.squaretakehomeapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Looper
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowNetworkCapabilities

@RunWith(DirectoryRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class NetworkStatusRepositoryImplTest {

    lateinit var connectivityManager: ConnectivityManager
    lateinit var networkCapabilities: NetworkCapabilities

    @Before
    fun setUp() {
        connectivityManager = getApplicationContext<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        shadowOf(Looper.getMainLooper()).idle()
        networkCapabilities = ShadowNetworkCapabilities.newInstance()
    }

    @Test
    fun `should be connected when connection is WiFi`() {
        shadowOf(networkCapabilities).addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        shadowOf(connectivityManager).setNetworkCapabilities(connectivityManager.activeNetwork, networkCapabilities)
        assertThat(connectivityManager.isActiveNetworkMetered).isTrue()
    }

    @Test
    fun `should be connected when connection is Cellular`() {
        shadowOf(networkCapabilities).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        shadowOf(connectivityManager).setNetworkCapabilities(connectivityManager.activeNetwork, networkCapabilities)
        assertThat(connectivityManager.isActiveNetworkMetered).isTrue()
    }
}